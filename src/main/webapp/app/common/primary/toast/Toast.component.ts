import { defineComponent, inject, onBeforeUnmount, onMounted, ref, Ref } from 'vue';
import { Unsubscribe } from '@/common/domain/alert/Unsubscribe';
import { ToastType } from '@/common/primary/toast/ToastType';
import { ToastMessage } from '@/common/primary/toast/ToastMessage';
import { AlertMessage } from '@/common/domain/alert/AlertMessage';
import { AlertListener } from '@/common/domain/alert/AlertListener';
import { IconVue } from '@/common/primary/icon';
import { TimeoutLauncher } from '@/common/primary/timeout/Timeout';

const WORD_TIME_MS = 300;
const TOAST_ATTENTION_MS = 1500;

const wordCount = (message: string): number => message.trim().split(/\s+/).length;

const wordsToMs = (message: ToastMessage): number => wordCount(message) * WORD_TIME_MS + TOAST_ATTENTION_MS;

export default defineComponent({
  name: 'Toast',

  components: {
    IconVue,
  },

  setup() {
    const alertListener = inject('alertListener') as AlertListener;
    const timeout = inject('timeout') as TimeoutLauncher;
    const toastTimeout = timeout();

    const toast = ref(null);
    const show = ref(false);
    const type: Ref<ToastType | undefined> = ref(undefined);
    const message: Ref<ToastMessage> = ref('');

    let unsubscribeSuccessAlertBus!: Unsubscribe;
    let unsubscribeErrorAlertBus!: Unsubscribe;

    const hideToast = () => {
      show.value = false;
      toastTimeout.unregister();
    };

    const showToast = () => {
      show.value = true;
      toastTimeout.register(() => hideToast(), wordsToMs(message.value));
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
      hideToast,
      show,
    };
  },
  methods: {
    close() {
      this.hideToast();
    },
  },
});
