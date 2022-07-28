import { ModuleProperties } from './ModuleProperties';
import { ProjectFolder } from './ProjectFolder';

export interface ModuleToApply {
  projectFolder: ProjectFolder;
  commit: boolean;
  properties: ModuleProperties;
}
