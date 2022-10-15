import { ModulePropertyDefinitionType } from './ModulePropertyDefinitionType';
import { ModulePropertyDescription } from './ModulePropertyDescription';
import { ModulePropertyDefaultValue } from './ModulePropertyDefaultValue';
import { ModulePropertyKey } from './ModulePropertyKey';

export interface ModulePropertyDefinition {
  type: ModulePropertyDefinitionType;
  mandatory: boolean;
  key: ModulePropertyKey;
  description?: ModulePropertyDescription;
  defaultValue?: ModulePropertyDefaultValue;
  order: number;
}
