import { defineComponent, inject, onBeforeUnmount, onMounted, ref, Ref } from 'vue';
import { Unsubscribe } from '@/common/domain/alert/Unsubscribe';
import { ToastType } from '@/common/primary/toast/ToastType';
import { ToastMessage } from '@/common/primary/toast/ToastMessage';
import { AlertMessage } from '@/common/domain/alert/AlertMessage';
import { Toast } from 'bootstrap';
import { nextTick } from '@vue/runtime-core';
import { AlertListener } from '@/common/domain/alert/AlertListener';

export default defineComponent({
  name: 'Toast',

  setup() {
    const alertListener = inject('alertListener') as AlertListener;

    const toast = ref(null);
    const bootstrapToast: Ref<Toast | undefined> = ref(undefined);
    const type: Ref<ToastType | undefined> = ref(undefined);
    const message: Ref<ToastMessage | undefined> = ref(undefined);

    let unsubscribeSuccessAlertBus!: Unsubscribe;
    let unsubscribeErrorAlertBus!: Unsubscribe;

    const showToast = async () => {
      await nextTick(() => {
        bootstrapToast.value!.show();
      });
    };

    const displaySuccess = (alertMessage: AlertMessage) => {
      type.value = ToastType.SUCCESS;
      message.value = alertMessage;
      showToast();
    };

    const displayError = (alertMessage: AlertMessage) => {
      type.value = ToastType.ERROR;
      message.value = alertMessage;
      showToast();
    };

    onMounted(() => {
      bootstrapToast.value = new Toast(toast.value as unknown as Element);
      unsubscribeSuccessAlertBus = alertListener.onSuccess(displaySuccess);
      unsubscribeErrorAlertBus = alertListener.onError(displayError);
    });

    onBeforeUnmount(() => {
      unsubscribeSuccessAlertBus();
      unsubscribeErrorAlertBus();
    });

    return {
      toast,
      type,
      message,
    };
  },
});
