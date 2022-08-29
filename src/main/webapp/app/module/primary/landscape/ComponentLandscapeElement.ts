import { Landscape } from '@/module/domain/landscape/Landscape';
import { LandscapeElement } from '@/module/domain/landscape/LandscapeElement';
import { LandscapeFeature } from '@/module/domain/landscape/LandscapeFeature';
import { LandscapeModule } from '@/module/domain/landscape/LandscapeModule';
import { ComponentLandscapeFeature } from './ComponentLandscapeFeature';
import { ComponentLandscapeModule } from './ComponentLandscapeModule';

export interface ComponentLandscapeElement {
  slug: string;
  dependantModules: string[];

  allModules(): ComponentLandscapeModule[];
  isFeature(): boolean;
}

export const convertFromLanscapeElement = (element: LandscapeElement, landscape: Landscape): ComponentLandscapeElement => {
  switch (element.type()) {
    case 'FEATURE':
      return ComponentLandscapeFeature.from(element as LandscapeFeature, landscape);
    case 'MODULE':
      return ComponentLandscapeModule.from(element as LandscapeModule, landscape);
  }
};
