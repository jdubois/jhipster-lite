import { Project } from '@/springboot/domain/Project';
import { RestGeneratorJHipster, toRestGeneratorJHipster } from './RestGeneratorJHipster';

export interface RestProject {
  folder: string;
  'generator-jhipster': RestGeneratorJHipster;
}

export const toRestProject = (project: Project): RestProject => ({
  folder: project.folder,
  'generator-jhipster': toRestGeneratorJHipster(project),
});

export const toProject = (restProject: RestProject): Project => ({
  folder: restProject.folder,
  baseName: restProject['generator-jhipster']?.baseName,
  packageName: restProject['generator-jhipster']?.packageName,
  projectName: restProject['generator-jhipster']?.projectName,
  serverPort: restProject['generator-jhipster']?.serverPort,
});
