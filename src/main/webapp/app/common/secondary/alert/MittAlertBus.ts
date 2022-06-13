import { AlertBus } from '@/common/domain/alert/AlertBus';
import { Emitter } from 'mitt';
import { AlertMessage } from '@/common/domain/alert/AlertMessage';
import { AlertType } from '@/common/secondary/alert/AlertType';

export class MittAlertBus implements AlertBus {
  constructor(private emitter: Emitter<any>) {}

  success(alert: AlertMessage): void {
    this.emitter.emit(AlertType.SUCCESS, alert);
  }

  error(alert: AlertMessage): void {
    this.emitter.emit(AlertType.ERROR, alert);
  }
}
