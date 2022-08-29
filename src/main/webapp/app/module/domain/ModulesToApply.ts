import { ModuleParameters } from './ModuleParameters';
import { ModuleSlug } from './ModuleSlug';
import { ProjectFolder } from './ProjectFolder';

export interface ModulesToApply {
  modules: ModuleSlug[];
  projectFolder: ProjectFolder;
  commit: boolean;
  parameters: ModuleParameters;
}
