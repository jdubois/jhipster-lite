import { ApplicationListener } from '@/common/primary/applicationlistener/ApplicationListener';
import { Loader } from '@/loader/primary/Loader';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import { defineComponent, inject, nextTick, onBeforeUnmount, onMounted, ref } from 'vue';
import { LandscapeModuleVue } from '../landscape-module';
import { buildConnector, LandscapeConnector } from './LandscapeConnector';
import { DisplayMode } from './DisplayMode';
import { ComponentLandscape } from './ComponentLandscape';
import { ComponentLandscapeModule } from './ComponentLandscapeModule';
import { ComponentLandscapeElement } from './ComponentLandscapeElement';
import { Optional } from '@/common/domain/Optional';
import { emptyLandscapeSize, LandscapeConnectorsSize } from './LandscapeConnectorsSize';
import { ModulePropertiesFormVue } from '../module-properties-form';
import { ModulePropertyDefinition } from '@/module/domain/ModulePropertyDefinition';
import { AlertBus } from '@/common/domain/alert/AlertBus';
import { ProjectHistory } from '@/module/domain/ProjectHistory';
import { ProjectFoldersRepository } from '@/module/domain/ProjectFoldersRepository';
import { ProjectActionsVue } from '../project-actions';
import { empty } from '../PropertyValue';
import { ModuleParameterType } from '@/module/domain/ModuleParameters';
import { ModuleParameter } from '@/module/domain/ModuleParameter';
import { Landscape } from '@/module/domain/landscape/Landscape';

export default defineComponent({
  name: 'LandscapeVue',
  components: { LandscapeModuleVue, ModulePropertiesFormVue, ProjectActionsVue },
  setup() {
    const alertBus = inject('alertBus') as AlertBus;
    const modules = inject('modules') as ModulesRepository;
    const projectFolders = inject('projectFolders') as ProjectFoldersRepository;
    const applicationListener = inject('applicationListener') as ApplicationListener;

    const selectedMode = ref<DisplayMode>('COMPACTED');

    const landscape = ref(Loader.loading<ComponentLandscape>());

    const landscapeContainer = ref<HTMLElement>();
    const landscapeConnectors = ref<LandscapeConnector[]>([]);
    const landscapeSize = ref<LandscapeConnectorsSize>(emptyLandscapeSize());
    const landscapeElements = ref(new Map<string, HTMLElement>());

    const emphasizedModule = ref<string>();

    const folderPath = ref('');
    const valuatedModuleParameters = ref(new Map<string, ModuleParameterType>());

    let commitModule = true;

    const operationInProgress = ref(false);

    onMounted(() => {
      modules.landscape().then(response => loadModules(response));

      projectFolders.get().then(projectFolder => (folderPath.value = projectFolder));
    });

    const loadModules = (response: Landscape): void => {
      landscape.value.loaded(ComponentLandscape.from(response));

      nextTick(() => {
        updateConnectors();
      });

      applicationListener.addEventListener('resize', updateConnectors);
    };

    onBeforeUnmount(() => {
      applicationListener.removeEventListener('resize', updateConnectors);
    });

    const updateConnectors = (): void => {
      landscapeConnectors.value = landscape.value
        .value()
        .levels.flatMap(level => level.elements)
        .flatMap(element => element.allModules())
        .flatMap(toConnectors);

      landscapeSize.value = buildConnectorsSize();
    };

    const toConnectors = (module: ComponentLandscapeModule): LandscapeConnector[] => {
      const dependantElement = landscapeElements.value.get(module.slug)!.getBoundingClientRect();

      return module.dependencies.flatMap(dependency =>
        buildConnector({
          dependantElement: dependantElement,
          dependantElementSlug: module.slug,
          dependencyElement: landscapeElements.value.get(dependency)!,
          dependencyElementSlug: dependency,
          container: landscapeContainer.value!,
        })
      );
    };

    const buildConnectorsSize = (): LandscapeConnectorsSize => ({
      width: Math.max.apply(
        null,
        landscapeConnectors.value.flatMap(connector => connector.positions).map(position => position.x)
      ),
      height: Math.max.apply(
        null,
        landscapeConnectors.value.flatMap(connector => connector.positions).map(position => position.y)
      ),
    });

    const isFeature = (element: ComponentLandscapeElement): boolean => {
      return element.isFeature();
    };

    const modeSwitchClass = (mode: DisplayMode): string => {
      if (selectedMode.value === mode) {
        return '-selected';
      }

      return '-not-selected';
    };

    const selectMode = (mode: DisplayMode): void => {
      selectedMode.value = mode;

      nextTick(() => {
        updateConnectors();
      });
    };

    const elementFlavor = (module: string): string => {
      return (
        operationInProgressClass() + highlightClass(module) + unselectionHighlightClass(module) + selectionClass(module) + flavorClass()
      );
    };

    const operationInProgressClass = (): string => {
      if (operationInProgress.value) {
        return ' -not-selectable';
      }

      return '';
    };

    const highlightClass = (module: string): string => {
      if (isHighlighted(module)) {
        if (isSelectable(module)) {
          return ' -selectable-highlighted';
        }

        return ' -not-selectable-highlighted';
      }

      return '';
    };

    const isHighlighted = (module: string): boolean => {
      if (emphasizedModule.value === undefined) {
        return false;
      }

      return emphasizedModule.value === module || landscapeValue().moduleSelectionTree(emphasizedModule.value).includes(module);
    };

    const unselectionHighlightClass = (module: string): string => {
      if (isUnselectedByEmphasizedModule(module)) {
        return ' -highlighted-unselection';
      }

      return '';
    };

    const selectionClass = (module: string): string => {
      if (isSelected(module)) {
        return ' -selected';
      }

      if (isSelectable(module)) {
        return ' -selectable';
      }

      return ' -not-selectable';
    };

    const flavorClass = (): string => {
      return ' ' + modeClass();
    };

    const modeClass = (): string => {
      switch (selectedMode.value) {
        case 'COMPACTED':
          return '-compacted';
        case 'EXTENDED':
          return '-extended';
      }
    };

    const emphasizeModule = (module: string): void => {
      emphasizedModule.value = module;
    };

    const deEmphasizeModule = (): void => {
      emphasizedModule.value = undefined;
    };

    const connectorClass = (startingElement: string): string => {
      if (emphasizedDependency(startingElement)) {
        if (isSelectable(startingElement)) {
          return '-selectable-highlighted';
        }

        return '-not-selectable-highlighted';
      }

      if (isUnselectedByEmphasizedModule(startingElement)) {
        return '-highlighted-unselection';
      }

      if (isSelected(startingElement)) {
        return '-selected';
      }

      return '';
    };

    const emphasizedDependency = (startingElement: string): boolean => {
      if (emphasizedModule.value === undefined) {
        return false;
      }

      const currentElementEmphasized = emphasizedModule.value === startingElement;
      const dependencyEmphasized = landscapeValue().moduleSelectionTree(emphasizedModule.value).includes(startingElement);

      return currentElementEmphasized || dependencyEmphasized;
    };

    const isUnselectedByEmphasizedModule = (module: string): boolean => {
      if (emphasizedModule.value === undefined) {
        return false;
      }

      if (!isSelected(module)) {
        return false;
      }

      return isDependantOfEmphasizedModule(module) || isDependantOfAFeatureSelectedModule(module);
    };

    const isDependantOfEmphasizedModule = (module: string): boolean => {
      return landscapeValue().dependantSelectedModules(emphasizedModule.value!).includes(module);
    };

    const isDependantOfAFeatureSelectedModule = (module: string): boolean => {
      return landscapeValue()
        .moduleFeature(emphasizedModule.value!)
        .flatMap(feature => Optional.ofUndefinable(feature.moduleSlugs.find(featureModule => landscapeValue().isSelected(featureModule))))
        .map(selectedModule => {
          const moduleDependsOnSelectedModule = landscapeValue().dependantSelectedModules(selectedModule).includes(module);
          const moduleDontDependOnEmphasizedModule = !landscapeValue().modulesToUnselect(emphasizedModule.value!).includes(module);

          return moduleDependsOnSelectedModule && moduleDontDependOnEmphasizedModule;
        })
        .orElse(false);
    };

    const isSelected = (module: string): boolean => {
      return landscapeValue().isSelected(module);
    };

    const toggleModule = (module: string): void => {
      landscapeValue().toggleModule(module);
    };

    const isSelectable = (element: string): boolean => {
      return landscapeValue().isSelectable(element);
    };

    const disabledApplication = (): boolean => {
      if (operationInProgress.value) {
        return true;
      }

      if (folderPath.value.length === 0) {
        return true;
      }

      if (landscapeValue().noSelectedModule()) {
        return true;
      }

      if (missingMandatoryProperty()) {
        return true;
      }

      return false;
    };

    const missingMandatoryProperty = () => {
      return selectedModulesProperties().some(property => property.mandatory && empty(valuatedModuleParameters.value.get(property.key)));
    };

    const selectedModulesProperties = (): ModulePropertyDefinition[] => {
      return landscapeValue().selectedModulesProperties();
    };

    const updateModuleCommit = (commit: boolean) => {
      commitModule = commit;
    };

    const updateFolderPath = (path: string): void => {
      folderPath.value = path;
    };

    const projectFolderUpdated = (): void => {
      modules
        .history(folderPath.value)
        .then(projectHistory => loadProjectHistory(projectHistory))
        .catch(() => {
          //Nothing to do
        });
    };

    const loadProjectHistory = (projectHistory: ProjectHistory): void => {
      projectHistory.modules.forEach(module => landscapeValue().selectModule(module.get()));

      projectHistory.properties.forEach(property => {
        if (unknownProperty(property.key)) {
          valuatedModuleParameters.value.set(property.key, property.value);
        }
      });
    };

    const unknownProperty = (key: string) => {
      return !valuatedModuleParameters.value.has(key);
    };

    const updateProperty = (property: ModuleParameter): void => {
      valuatedModuleParameters.value.set(property.key, property.value);
    };

    const deleteProperty = (key: string): void => {
      valuatedModuleParameters.value.delete(key);
    };

    const landscapeValue = (): ComponentLandscape => {
      return landscape.value.value();
    };

    const applyModules = (): void => {
      operationStarted();

      modules
        .applyAll({
          modules: landscapeValue().selectedModulesSlugs(),
          projectFolder: folderPath.value,
          commit: commitModule,
          parameters: valuatedModuleParameters.value,
        })
        .then(() => {
          operationEnded();

          alertBus.success('Modules applied');
        })
        .catch(() => {
          operationEnded();

          alertBus.error('Modules not applied');
        });
    };

    const operationStarted = (): void => {
      operationInProgress.value = true;
    };

    const operationEnded = (): void => {
      operationInProgress.value = false;
    };

    return {
      landscape,
      isFeature,
      landscapeConnectors,
      landscapeSize,
      landscapeElements,
      landscapeContainer,
      modeSwitchClass,
      selectMode,
      modeClass,
      emphasizeModule,
      deEmphasizeModule,
      connectorClass,
      elementFlavor,
      toggleModule,
      disabledApplication,
      folderPath,
      selectedModulesProperties,
      valuatedModuleParameters,
      updateModuleCommit,
      updateFolderPath,
      projectFolderUpdated,
      updateProperty,
      deleteProperty,
      applyModules,
      operationStarted,
      operationEnded,
    };
  },
});
