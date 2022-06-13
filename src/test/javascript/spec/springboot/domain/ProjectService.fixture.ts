import { ProjectService } from '@/springboot/domain/ProjectService';
import sinon, { SinonStub } from 'sinon';

export interface ProjectServiceFixture extends ProjectService {
  init: SinonStub;
  addMaven: SinonStub;
  addJaCoCo: SinonStub;
  addSonarBackend: SinonStub;
  addSonarBackendFrontend: SinonStub;
  addFrontendMavenPlugin: SinonStub;
  addJavaBase: SinonStub;
  download: SinonStub;
  addCodespacesSetup: SinonStub;
  addGitpodSetup: SinonStub;
  getProjectDetails: SinonStub;
}

export const stubProjectService = (): ProjectServiceFixture => ({
  init: sinon.stub(),
  addMaven: sinon.stub(),
  addJaCoCo: sinon.stub(),
  addSonarBackend: sinon.stub(),
  addSonarBackendFrontend: sinon.stub(),
  addFrontendMavenPlugin: sinon.stub(),
  addJavaBase: sinon.stub(),
  download: sinon.stub(),
  addCodespacesSetup: sinon.stub(),
  addGitpodSetup: sinon.stub(),
  getProjectDetails: sinon.stub(),
});
