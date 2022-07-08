import { Project } from '@/springboot/domain/Project';
import { DocumentFile } from '@/common/domain/DocumentFile';

export interface ProjectService {
  init(project: Project): Promise<void>;
  addMaven(project: Project): Promise<void>;
  addJaCoCo(project: Project): Promise<void>;
  addSonarBackend(project: Project): Promise<void>;
  addSonarBackendFrontend(project: Project): Promise<void>;
  addFrontendMavenPlugin(project: Project): Promise<void>;
  addJavaBase(project: Project): Promise<void>;
  download(project: Project): Promise<DocumentFile>;
  addCodespacesSetup(project: Project): Promise<void>;
  addGitpodSetup(project: Project): Promise<void>;
  getProjectDetails(folder: string): Promise<void>;
  addCypress(project: Project): Promise<void>;
  addPlaywright(project: Project): Promise<void>;
}
