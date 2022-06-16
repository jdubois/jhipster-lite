import sinon, { SinonStub } from 'sinon';
import { AngularService } from '@/springboot/domain/client/AngularService';

export interface AngularServiceFixture extends AngularService {
  add: SinonStub;
  addWithJWT: SinonStub;
  addOauth2: SinonStub;
  addHealth: SinonStub;
}

export const stubAngularService = (): AngularServiceFixture => ({
  add: sinon.stub(),
  addWithJWT: sinon.stub(),
  addOauth2: sinon.stub(),
  addHealth: sinon.stub(),
});
