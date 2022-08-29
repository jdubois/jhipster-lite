import { ModuleToApply } from './ModuleToApply';
import { Modules } from './Modules';
import { ModuleSlug } from './ModuleSlug';
import { ProjectFolder } from './ProjectFolder';
import { Project } from './Project';
import { ProjectHistory } from './ProjectHistory';
import { Landscape } from './landscape/Landscape';
import { ModulesToApply } from './ModulesToApply';

export interface ModulesRepository {
  list(): Promise<Modules>;

  landscape(): Promise<Landscape>;

  apply(module: ModuleSlug, moduleToApply: ModuleToApply): Promise<void>;

  applyAll(modulesToApply: ModulesToApply): Promise<void>;

  history(folder: ProjectFolder): Promise<ProjectHistory>;

  format(folder: ProjectFolder): Promise<void>;

  download(folder: ProjectFolder): Promise<Project>;
}
