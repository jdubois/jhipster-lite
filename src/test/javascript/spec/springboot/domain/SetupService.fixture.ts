import { SetupService } from '../../../../../main/webapp/app/springboot/domain/SetupService';
import sinon, { SinonStub } from 'sinon';

export interface SetupServiceFixture extends SetupService {
  addGithubActions: SinonStub;
}

export const stubSetupService = (): SetupServiceFixture => ({
  addGithubActions: sinon.stub(),
});
