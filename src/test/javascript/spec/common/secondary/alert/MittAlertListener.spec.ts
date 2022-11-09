import sinon from 'sinon';
import mitt from 'mitt';
import { MittAlertListener } from '@/common/secondary/alert/MittAlertListener';
import { AlertType } from '@/common/secondary/alert/AlertType';
import { describe, it, expect } from 'vitest';

describe('MittAlertListener', () => {
  it('should listen sent success message', () => {
    const spyAlerted = sinon.spy();
    const emitter = mitt();
    const mittAlertBus = new MittAlertListener(emitter);
    mittAlertBus.onSuccess(spyAlerted);

    emitter.emit(AlertType.SUCCESS, 'A message');

    const [alerted] = spyAlerted.getCall(0).args;
    expect(alerted).toBe('A message');
  });

  it('should unsubscribe success', () => {
    const spyAlerted = sinon.spy();
    const emitter = mitt();
    const mittAlertBus = new MittAlertListener(emitter);
    const unsubscribe = mittAlertBus.onSuccess(spyAlerted);

    emitter.emit(AlertType.SUCCESS, 'A message');
    emitter.emit(AlertType.SUCCESS, 'A message');
    unsubscribe();
    emitter.emit(AlertType.SUCCESS, 'A message');
    emitter.emit(AlertType.SUCCESS, 'A message');

    expect(spyAlerted.callCount).toBe(2);
  });

  it('should listen sent error message', () => {
    const spyAlerted = sinon.spy();
    const emitter = mitt();
    const mittAlertBus = new MittAlertListener(emitter);
    mittAlertBus.onError(spyAlerted);

    emitter.emit(AlertType.ERROR, 'A message');

    const [alerted] = spyAlerted.getCall(0).args;
    expect(alerted).toBe('A message');
  });

  it('should unsubscribe error', () => {
    const spyAlerted = sinon.spy();
    const emitter = mitt();
    const mittAlertBus = new MittAlertListener(emitter);
    const unsubscribe = mittAlertBus.onError(spyAlerted);

    emitter.emit(AlertType.ERROR, 'A message');
    emitter.emit(AlertType.ERROR, 'A message');
    unsubscribe();
    emitter.emit(AlertType.ERROR, 'A message');
    emitter.emit(AlertType.ERROR, 'A message');

    expect(spyAlerted.callCount).toBe(2);
  });
});
