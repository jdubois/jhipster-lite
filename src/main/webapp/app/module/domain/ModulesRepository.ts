import { ModuleToApply } from './ModuleToApply';
import { Modules } from './Modules';
import { ModuleSlug } from './ModuleSlug';
import { ProjectFolder } from './ProjectFolder';

export interface ModulesRepository {
  list(): Promise<Modules>;

  apply(module: ModuleSlug, moduleToApply: ModuleToApply): Promise<void>;

  appliedModules(folder: ProjectFolder): Promise<ModuleSlug[]>;
}
