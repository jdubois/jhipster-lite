import { ModulePropertyDefinition } from '../domain/ModulePropertyDefinition';
import { ModulePropertyDefinitionType } from '../domain/ModulePropertyDefinitionType';

export interface RestModulePropertyDefinition {
  type: ModulePropertyDefinitionType;
  mandatory: boolean;
  key: string;
  description?: string;
  example?: string;
  order: number;
}

export const toPropertyDefinition = (propertyDefinition: RestModulePropertyDefinition): ModulePropertyDefinition => ({
  type: propertyDefinition.type,
  mandatory: propertyDefinition.mandatory,
  key: propertyDefinition.key,
  description: propertyDefinition.description,
  example: propertyDefinition.example,
  order: propertyDefinition.order,
});
