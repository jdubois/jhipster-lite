import { LandscapeElement } from '../domain/landscape/LandscapeElement';
import { RestLandscapeFeature, toLandscapeFeature } from './RestLandscapeFeature';
import { RestLandscapeModule, toLandscapeModule } from './RestLandscapeModule';

export type RestLandscapeElement = RestLandscapeModule | RestLandscapeFeature;

export const toLandscapeElement = (element: RestLandscapeElement): LandscapeElement => {
  switch (element.type) {
    case 'FEATURE':
      return toLandscapeFeature(element as RestLandscapeFeature);
    case 'MODULE':
      return toLandscapeModule(element as RestLandscapeModule);
  }
};
