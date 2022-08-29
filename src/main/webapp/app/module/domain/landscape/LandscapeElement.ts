import { LandscapeElementId } from './LandscapeElementId';
import { LandscapeElementType } from './LandscapeElementType';
import { LandscapeModule } from './LandscapeModule';

export interface LandscapeElement {
  slug: LandscapeElementId;
  type(): LandscapeElementType;
  allModules(): LandscapeModule[];
}
