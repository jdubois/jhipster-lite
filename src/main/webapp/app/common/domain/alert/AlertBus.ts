import { AlertMessage } from '@/common/domain/alert/AlertMessage';

export interface AlertBus {
  success(alertMessage: AlertMessage): void;
  error(alertMessage: AlertMessage): void;
}
