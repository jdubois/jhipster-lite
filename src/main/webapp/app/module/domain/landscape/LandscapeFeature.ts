import { LandscapeModule } from './LandscapeModule';
import { LandscapeFeatureSlug } from './LandscapeFeatureSlug';
import { LandscapeElement } from './LandscapeElement';
import { LandscapeElementType } from './LandscapeElementType';

export class LandscapeFeature implements LandscapeElement {
  constructor(public readonly slug: LandscapeFeatureSlug, public readonly modules: LandscapeModule[]) {}

  public type(): LandscapeElementType {
    return 'FEATURE';
  }

  public allModules(): LandscapeModule[] {
    return this.modules;
  }
}
