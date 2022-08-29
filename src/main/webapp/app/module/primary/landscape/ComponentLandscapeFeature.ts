import { Landscape } from '@/module/domain/landscape/Landscape';
import { LandscapeFeature } from '@/module/domain/landscape/LandscapeFeature';
import { ComponentLandscapeElement } from './ComponentLandscapeElement';
import { ComponentLandscapeModule } from './ComponentLandscapeModule';

export class ComponentLandscapeFeature implements ComponentLandscapeElement {
  public readonly moduleSlugs: string[];

  private constructor(
    public readonly slug: string,
    public readonly modules: ComponentLandscapeModule[],
    public readonly dependantModules: string[]
  ) {
    this.moduleSlugs = modules.map(module => module.slug);
  }

  public static from(feature: LandscapeFeature, landscape: Landscape): ComponentLandscapeFeature {
    return new ComponentLandscapeFeature(
      feature.slug.get(),
      feature.modules.map(module => ComponentLandscapeModule.from(module, landscape)),
      landscape.dependantModules(feature).map(module => module.get())
    );
  }

  public allModules(): ComponentLandscapeModule[] {
    return this.modules;
  }

  public isFeature(): boolean {
    return true;
  }
}
