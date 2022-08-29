import { ModulePropertyDefinitionType } from './ModulePropertyDefinitionType';
import { ModulePropertyDescription } from './ModulePropertyDescription';
import { ModulePropertyExample } from './ModulePropertyExample';
import { ModulePropertyKey } from './ModulePropertyKey';

export interface ModulePropertyDefinition {
  type: ModulePropertyDefinitionType;
  mandatory: boolean;
  key: ModulePropertyKey;
  description?: ModulePropertyDescription;
  example?: ModulePropertyExample;
  order: number;
}
