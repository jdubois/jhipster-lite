import { ModuleProperties } from './ModuleProperties';
import { Modules } from './Modules';
import { ModuleSlug } from './ModuleSlug';

export interface ModulesRepository {
  list(): Promise<Modules>;
  get(slug: ModuleSlug): Promise<ModuleProperties>;
}
