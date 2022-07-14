import { ProjectFolderService } from '@/springboot/domain/ProjectFolderService';
import sinon, { SinonStub } from 'sinon';

export interface ProjectFolderServiceFixture extends ProjectFolderService {
  get: SinonStub;
}

export const stubProjectFolderService = (): ProjectFolderServiceFixture => ({
  get: sinon.stub(),
});
