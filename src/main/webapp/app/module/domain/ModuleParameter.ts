import { ModuleParameterType } from './ModuleParameters';
import { ModulePropertyKey } from './ModulePropertyKey';

export interface ModuleParameter {
  key: ModulePropertyKey;
  value: ModuleParameterType;
}
