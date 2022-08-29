import { LandscapeLevel } from '../domain/landscape/LandscapeLevel';
import { RestLandscapeElement, toLandscapeElement } from './RestLandscapeElement';

export interface RestLandscapeLevel {
  elements: RestLandscapeElement[];
}

export const toLandscapeLevel = (level: RestLandscapeLevel): LandscapeLevel => ({
  elements: level.elements.map(toLandscapeElement),
});
