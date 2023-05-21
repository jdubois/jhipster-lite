import { ModuleParameterType } from '@/module/domain/ModuleParameters';

export interface ModuleParametersRepository {
  store(map: Map<string, ModuleParameterType>): void;
  get(): Map<string, ModuleParameterType>;
}
