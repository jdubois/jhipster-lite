import sinon, { SinonStub } from 'sinon';
import { VueService } from '@/springboot/domain/client/VueService';

export interface VueServiceFixture extends VueService {
  add: SinonStub;
}

export const stubVueService = (): VueServiceFixture => ({
  add: sinon.stub(),
});
