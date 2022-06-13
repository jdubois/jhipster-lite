import { shallowMount, VueWrapper } from '@vue/test-utils';
import { ToastVue } from '@/common/primary/toast';
import sinon from 'sinon';
import { ToastType } from '@/common/primary/toast/ToastType';
import { AlertListener } from '@/common/domain/alert/AlertListener';
import { stubAlertListener } from '../../domain/AlertListener.fixure';

let wrapper: VueWrapper;
let component: any;

interface WrapperOptions {
  alertListener: AlertListener;
}

const wrap = (wrapperOptions?: Partial<WrapperOptions>) => {
  const { alertListener }: WrapperOptions = {
    alertListener: stubAlertListener(),
    ...wrapperOptions,
  };
  wrapper = shallowMount(ToastVue, {
    global: {
      provide: {
        alertListener,
      },
    },
  });
  component = wrapper.vm;
};

describe('Toast', () => {
  it('should exist', () => {
    wrap();

    expect(wrapper.exists()).toBe(true);
  });

  it('should display toast on success', () => {
    const alertListener = stubAlertListener();
    wrap({ alertListener });

    const [callback] = alertListener.onSuccess.getCall(0).args;
    callback('message');

    expect(component.message).toBe('message');
    expect(component.type).toBe(ToastType.SUCCESS);
  });

  it('should display toast on error', () => {
    const alertListener = stubAlertListener();
    wrap({ alertListener });

    const [callback] = alertListener.onError.getCall(0).args;
    callback('message');

    expect(component.message).toBe('message');
    expect(component.type).toBe(ToastType.ERROR);
  });

  it('should unsubscribe on before unmount', () => {
    const alertListener = stubAlertListener();
    const unsubscribeSuccess = sinon.stub();
    alertListener.onSuccess.returns(unsubscribeSuccess);
    const unsubscribeError = sinon.stub();
    alertListener.onError.returns(unsubscribeError);
    wrap({ alertListener });

    wrapper.unmount();

    expect(unsubscribeSuccess.calledOnce).toBe(true);
    expect(unsubscribeError.calledOnce).toBe(true);
  });
});
