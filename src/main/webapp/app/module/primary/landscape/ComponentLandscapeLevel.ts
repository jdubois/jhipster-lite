import { Landscape } from '@/module/domain/landscape/Landscape';
import { LandscapeLevel } from '@/module/domain/landscape/LandscapeLevel';
import { ComponentLandscapeElement, convertFromLanscapeElement } from './ComponentLandscapeElement';

export class ComponentLandscapeLevel {
  private constructor(public readonly elements: ComponentLandscapeElement[]) {}

  static from(level: LandscapeLevel, landscape: Landscape): ComponentLandscapeLevel {
    return new ComponentLandscapeLevel(level.elements.map(element => convertFromLanscapeElement(element, landscape)));
  }
}
