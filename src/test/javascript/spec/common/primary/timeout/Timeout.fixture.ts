import sinon, { SinonStub } from 'sinon';
import { Timeoutable, TimeoutListener } from '@/common/primary/timeout/Timeout';

interface TimeoutListenerStub extends TimeoutListener {
  register: SinonStub<[Timeoutable, number]>;
  unregister: SinonStub;
}

export const stubTimeout = (): TimeoutListenerStub => ({
  register: sinon.stub<[Timeoutable, number]>(),
  unregister: sinon.stub(),
});
