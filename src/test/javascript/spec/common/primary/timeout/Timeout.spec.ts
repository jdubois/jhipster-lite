import { Timeout } from '@/common/primary/timeout/Timeout';
import sinon from 'sinon';
import { describe, expect, it, vi, beforeEach, afterEach } from 'vitest';

const TIMEOUT_TIME = 3000;
const LESS_TIME = 1000;

describe('Timeout', () => {
  beforeEach(() => {
    vi.useFakeTimers();
  });

  afterEach(() => {
    vi.useRealTimers();
  });

  it('Should launch timeout after passed time', () => {
    const stub = sinon.stub();
    new Timeout().register(stub, TIMEOUT_TIME);

    vi.advanceTimersByTime(TIMEOUT_TIME);

    expect(stub.callCount).toBe(1);
  });

  it('Should not launch timeout with less some time', () => {
    const stub = sinon.stub();
    new Timeout().register(stub, TIMEOUT_TIME);

    vi.advanceTimersByTime(LESS_TIME);

    expect(stub.callCount).toBe(0);
  });

  it('Should not launch timeout with unsubscribe', () => {
    const stub = sinon.stub();
    const timeout = new Timeout();
    timeout.register(stub, TIMEOUT_TIME);

    timeout.unregister();
    vi.advanceTimersByTime(TIMEOUT_TIME);

    expect(stub.callCount).toBe(0);
  });

  it('Should not fail to unregister when not registered', () => {
    const timeout = new Timeout();

    expect(() => timeout.unregister()).not.toThrow();
  });

  it('Should clear previous registration before register another one', () => {
    const timeout = new Timeout();
    const firstCall = sinon.stub();
    const secondCall = sinon.stub();

    timeout.register(firstCall, TIMEOUT_TIME);
    vi.advanceTimersByTime(LESS_TIME);
    timeout.register(secondCall, TIMEOUT_TIME);
    vi.advanceTimersByTime(TIMEOUT_TIME);

    expect(firstCall.callCount).toBe(0);
    expect(secondCall.callCount).toBe(1);
  });
});
