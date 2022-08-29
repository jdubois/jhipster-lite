import { ModulePropertyDefinition } from '../ModulePropertyDefinition';
import { ModuleSlug } from '../ModuleSlug';
import { LandscapeElement } from './LandscapeElement';
import { LandscapeElementId } from './LandscapeElementId';
import { LandscapeElementType } from './LandscapeElementType';
import { ModuleOperation } from './ModuleOperation';

export class LandscapeModule implements LandscapeElement {
  constructor(
    public readonly slug: ModuleSlug,
    public readonly operation: ModuleOperation,
    public readonly properties: ModulePropertyDefinition[],
    public readonly dependencies: LandscapeElementId[]
  ) {}

  public type(): LandscapeElementType {
    return 'MODULE';
  }

  public allModules(): LandscapeModule[] {
    return [this];
  }
}
