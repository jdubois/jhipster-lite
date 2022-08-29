import { Landscape } from '@/module/domain/landscape/Landscape';
import { ComponentLandscapeElement } from './ComponentLandscapeElement';
import { ComponentLandscapeFeature } from './ComponentLandscapeFeature';
import { ComponentLandscapeLevel } from './ComponentLandscapeLevel';
import { ComponentLandscapeModule } from './ComponentLandscapeModule';
import { Memoizer } from '../../../common/domain/Memoizer';
import { Optional } from '../../../common/domain/Optional';
import { ModulePropertyDefinition } from '@/module/domain/ModulePropertyDefinition';
import { ModuleSlug } from '@/module/domain/ModuleSlug';

type ElementSlug = string;

export class ComponentLandscape {
  private readonly elements: Map<ElementSlug, ComponentLandscapeElement>;
  private readonly moduleFeatures: Map<ElementSlug, ComponentLandscapeFeature>;

  private readonly memoizedDependenciesFor = new Memoizer<ElementSlug, ComponentLandscapeElement[]>();
  private readonly memoizedDependantModules = new Memoizer<ElementSlug, ElementSlug[]>();
  private readonly memoizedModulesToUnselect = new Memoizer<ElementSlug, ElementSlug[]>();
  private readonly memoizedSelectionTree = new Memoizer<ElementSlug, ElementSlug[]>();
  private readonly memoizedSelectable = new Memoizer<ElementSlug, boolean>();
  private readonly memoizedModuleProperties = new Memoizer<boolean, ModulePropertyDefinition[]>();

  private selectedModules: ElementSlug[] = [];

  private constructor(public readonly levels: ComponentLandscapeLevel[]) {
    this.elements = this.buildElements();
    this.moduleFeatures = this.buildModuleFeatures();
  }

  private buildElements(): Map<ElementSlug, ComponentLandscapeElement> {
    return new Map(
      this.levels
        .flatMap(level => level.elements)
        .flatMap(element => [element, ...element.allModules()].map(landscapeElement => [landscapeElement.slug, landscapeElement]))
    );
  }

  private buildModuleFeatures(): Map<ElementSlug, ComponentLandscapeFeature> {
    return new Map(
      Array.from(this.elements.values())
        .filter(element => element.isFeature())
        .map(element => element as ComponentLandscapeFeature)
        .flatMap(feature => feature.moduleSlugs.map(moduleSlug => [moduleSlug, feature]))
    );
  }

  public static from(landscape: Landscape): ComponentLandscape {
    return new ComponentLandscape(landscape.levels.map(level => ComponentLandscapeLevel.from(level, landscape)));
  }

  public toggleModule(module: ElementSlug) {
    if (this.isSelected(module)) {
      this.unselectModule(module);
    } else if (this.isSelectable(module)) {
      this.selectModuleAndDependencies(module);
    }
  }
  private clearMemoizers() {
    this.memoizedDependantModules.clear();
    this.memoizedModulesToUnselect.clear();
    this.memoizedSelectionTree.clear();
    this.memoizedSelectable.clear();
    this.memoizedModuleProperties.clear();
  }

  public isSelected(module: ElementSlug): boolean {
    return this.selectedModules.includes(module);
  }

  private selectModuleAndDependencies(module: ElementSlug): void {
    this.selectModule(module);

    this.moduleSelectionTree(module).forEach(moduleDependency => this.selectModule(moduleDependency));
  }

  public selectModule(module: ElementSlug): void {
    if (this.unknownElement(module) || this.selectedModules.includes(module)) {
      return;
    }

    this.clearMemoizers();

    this.selectedModules.push(module);

    this.moduleFeature(module).ifPresent(feature =>
      feature.moduleSlugs.filter(featureModule => featureModule !== module).forEach(featureModule => this.unselectModule(featureModule))
    );

    this.clearMemoizers();
  }

  private unknownElement(module: ElementSlug) {
    return !this.elements.has(module);
  }

  private unselectModule(module: ElementSlug): void {
    this.clearMemoizers();

    const dependantModules = this.dependantSelectedModules(module);

    this.selectedModules = this.selectedModules.filter(
      selectedModule => selectedModule !== module && !dependantModules.includes(selectedModule)
    );

    this.clearMemoizers();
  }

  public dependantSelectedModules(module: ElementSlug): ElementSlug[] {
    return this.memoizedDependantModules.get(module, () =>
      this.allDependantSelectedModules(module, element => this.elementDependantModules(element))
    );
  }

  private elementDependantModules(element: ElementSlug): ElementSlug[] {
    const directDependantModules = this.findElement(element).dependantModules;

    return this.moduleFeature(element)
      .map(feature => {
        if (this.switchModuleInFeature(feature, element)) {
          return directDependantModules;
        }

        return [...directDependantModules, ...feature.dependantModules];
      })
      .orElse(directDependantModules);
  }

  public modulesToUnselect(module: ElementSlug): ElementSlug[] {
    return this.memoizedModulesToUnselect.get(module, () =>
      this.allDependantSelectedModules(module, element => this.elementsToUnselect(element))
    );
  }

  private elementsToUnselect(element: ElementSlug): ElementSlug[] {
    const directDependantModules = this.findElement(element).dependantModules;

    return this.moduleFeature(element)
      .map(feature => [...directDependantModules, ...feature.dependantModules])
      .orElse(directDependantModules);
  }

  private allDependantSelectedModules(element: ElementSlug, dependenciesSelector: (element: ElementSlug) => ElementSlug[]): ElementSlug[] {
    const dependantModules = dependenciesSelector(element);

    const selectedDependantModules = [
      ...new Set(dependantModules.filter(dependantModule => this.selectedModules.includes(dependantModule))),
    ];

    if (selectedDependantModules.length === 0) {
      return [];
    }

    return [
      ...selectedDependantModules,
      ...selectedDependantModules.flatMap(dependantModule => this.allDependantSelectedModules(dependantModule, dependenciesSelector)),
    ];
  }

  private switchModuleInFeature(feature: ComponentLandscapeFeature, element: ElementSlug): boolean {
    return feature.moduleSlugs.some(featureModule => this.isSelected(featureModule) && featureModule !== element);
  }

  public isSelectable(slug: ElementSlug): boolean {
    return this.memoizedSelectable.get(slug, () => {
      const element = this.findElement(slug);

      if (element instanceof ComponentLandscapeFeature) {
        return this.isElementSelectableInFeature(element);
      }

      const moduleDependencies = this.dependenciesFor(slug);
      if (moduleDependencies.length === 0) {
        return true;
      }

      const onlySelectableDependencies = moduleDependencies
        .filter(dependency => dependency.isFeature())
        .map(dependency => dependency as ComponentLandscapeFeature)
        .every(landscapeFeature => this.isElementSelectableInFeature(landscapeFeature));

      const noIncompatibleFeatureSelection = (element as ComponentLandscapeModule).dependencies.every(dependency =>
        this.noImcompatibleModuleSelection(dependency)
      );

      return onlySelectableDependencies && noIncompatibleFeatureSelection;
    });
  }

  private isElementSelectableInFeature(feature: ComponentLandscapeFeature): boolean {
    const oneSelectableModule = feature.moduleSlugs.length === 1 && this.isSelectable(feature.moduleSlugs[0]);
    const oneSelectedModule = feature.moduleSlugs.some(selectedFeatureModule => this.selectedModules.includes(selectedFeatureModule));

    return oneSelectableModule || oneSelectedModule;
  }

  private noImcompatibleModuleSelection(dependencySlug: ElementSlug): boolean {
    return this.moduleFeature(dependencySlug)
      .map(feature =>
        feature
          .allModules()
          .filter(featureModule => featureModule.slug !== dependencySlug)
          .every(featureModule => !this.isSelected(featureModule.slug))
      )
      .orElse(true);
  }

  public moduleSelectionTree(module: ElementSlug): ElementSlug[] {
    return this.memoizedSelectionTree.get(module, () =>
      this.dependenciesFor(module)
        .map(dependency => this.toModuleSelectionTree(dependency))
        .map(dependency => dependency.slug)
    );
  }

  private toModuleSelectionTree(dependency: ComponentLandscapeElement): ComponentLandscapeElement {
    if (dependency instanceof ComponentLandscapeFeature && dependency.moduleSlugs.length === 1) {
      return dependency.modules[0];
    }

    return this.moduleFeature(dependency.slug)
      .flatMap(feature =>
        Optional.ofUndefinable(
          feature.modules.find(featureModule => this.selectedModules.includes(featureModule.slug)) as ComponentLandscapeElement
        )
      )
      .orElse(dependency);
  }

  private dependenciesFor(slug: ElementSlug): ComponentLandscapeElement[] {
    return this.memoizedDependenciesFor.get(slug, () => {
      const element = this.findElement(slug);

      const moduleDependencies = element.allModules().flatMap(module => module.dependencies);

      if (moduleDependencies.length === 0) {
        return [];
      }

      const directDependencies = moduleDependencies.map(dependency => this.findElement(dependency));
      const nestedDependencies = moduleDependencies.flatMap(dependency => this.dependenciesFor(dependency));

      return [...new Set([...directDependencies, ...nestedDependencies])];
    });
  }

  private findElement(slug: ElementSlug): ComponentLandscapeElement {
    const element = this.elements.get(slug);

    if (element === undefined) {
      throw new Error(`Element ${slug} can't be found`);
    }

    return element;
  }

  public moduleFeature(slug: ElementSlug): Optional<ComponentLandscapeFeature> {
    return Optional.ofUndefinable(this.moduleFeatures.get(slug));
  }

  public noSelectedModule(): boolean {
    return this.selectedModules.length === 0;
  }

  public selectedModulesProperties(): ModulePropertyDefinition[] {
    return this.memoizedModuleProperties.get(true, () =>
      this.selectedModules
        .map(module => this.findElement(module) as ComponentLandscapeModule)
        .flatMap(module => module.properties)
        .filter(property => property !== undefined)
        .reduce(this.toArrayWithoutDuplicates(), [])
        .sort((first, second) => first.order - second.order)
    );
  }

  private toArrayWithoutDuplicates(): (
    result: ModulePropertyDefinition[],
    currentValue: ModulePropertyDefinition
  ) => ModulePropertyDefinition[] {
    return (result: ModulePropertyDefinition[], current: ModulePropertyDefinition) => {
      if (this.doNotContainProperty(result, current)) {
        result.push(current);
      }

      return result;
    };
  }

  private doNotContainProperty(result: ModulePropertyDefinition[], current: ModulePropertyDefinition): boolean {
    return !result.some(knownProperty => knownProperty.key === current.key);
  }

  selectedModulesSlugs(): ModuleSlug[] {
    return this.selectedModules.map(module => new ModuleSlug(module));
  }
}
