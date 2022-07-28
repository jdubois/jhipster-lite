import { ModuleToApply } from './ModuleToApply';
import { Modules } from './Modules';
import { ModuleSlug } from './ModuleSlug';
import { ProjectFolder } from './ProjectFolder';
import { Project } from './Project';
import { ProjectHistory } from './ProjectHistory';

export interface ModulesRepository {
  list(): Promise<Modules>;

  apply(module: ModuleSlug, moduleToApply: ModuleToApply): Promise<void>;

  history(folder: ProjectFolder): Promise<ProjectHistory>;

  download(folder: ProjectFolder): Promise<Project>;
}
