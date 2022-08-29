import { ModuleSlug } from '../ModuleSlug';
import { LandscapeElement } from './LandscapeElement';
import { LandscapeLevel } from './LandscapeLevel';

export class Landscape {
  constructor(public readonly levels: LandscapeLevel[]) {}

  dependantModules(element: LandscapeElement): ModuleSlug[] {
    return this.levels
      .flatMap(level => level.elements)
      .flatMap(landscapeElement => landscapeElement.allModules())
      .filter(module => module.dependencies.some(dependency => dependency.get() === element.slug.get()))
      .map(module => module.slug);
  }
}
