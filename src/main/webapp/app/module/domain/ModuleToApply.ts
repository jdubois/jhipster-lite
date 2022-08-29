import { ModuleParameters } from './ModuleParameters';
import { ProjectFolder } from './ProjectFolder';

export interface ModuleToApply {
  projectFolder: ProjectFolder;
  commit: boolean;
  parameters: ModuleParameters;
}
