import { Project } from '@/springboot/domain/Project';

export interface SetupService {
  addGithubActions(project: Project): Promise<void>;
}
