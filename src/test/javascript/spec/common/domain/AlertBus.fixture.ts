import sinon, { SinonStub } from 'sinon';
import { AlertBus } from '@/common/domain/alert/AlertBus';

export interface AlertBusFixture extends AlertBus {
  success: SinonStub;
  error: SinonStub;
}

export const stubAlertBus = (): AlertBusFixture => ({
  success: sinon.stub(),
  error: sinon.stub(),
});
