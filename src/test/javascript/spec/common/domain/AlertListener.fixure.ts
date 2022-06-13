import sinon, { SinonStub } from 'sinon';
import { AlertListener } from '@/common/domain/alert/AlertListener';

export interface AlertListenerFixture extends AlertListener {
  onSuccess: SinonStub;
  onError: SinonStub;
}

export const stubAlertListener = (): AlertListenerFixture => ({
  onSuccess: sinon.stub(),
  onError: sinon.stub(),
});
