import { ModulePropertyDefinition } from '../domain/ModulePropertyDefinition';
import { RestModulePropertyDefinition, toPropertyDefinition } from './RestModulePropertyDefinition';

export interface RestModulePropertiesDefinitions {
  definitions: RestModulePropertyDefinition[];
}

export const toPropertiesDefinitions = (propertiesDefinitions: RestModulePropertiesDefinitions | undefined): ModulePropertyDefinition[] => {
  if (!propertiesDefinitions) {
    return [];
  }

  return propertiesDefinitions.definitions.map(toPropertyDefinition);
};
