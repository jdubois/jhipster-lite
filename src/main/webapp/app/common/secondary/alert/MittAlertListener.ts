import { Alerted } from '@/common/domain/alert/Alerted';
import { Unsubscribe } from '@/common/domain/alert/Unsubscribe';
import { Emitter, Handler } from 'mitt';
import { AlertMessage } from '@/common/domain/alert/AlertMessage';
import { AlertType } from '@/common/secondary/alert/AlertType';
import { AlertListener } from '@/common/domain/alert/AlertListener';

export class MittAlertListener implements AlertListener {
  constructor(private emitter: Emitter<any>) {}

  onSuccess(alerted: Alerted): Unsubscribe {
    const handler: Handler<AlertMessage> = message => alerted(message);
    this.emitter.on(AlertType.SUCCESS, handler);
    return () => {
      this.emitter.off(AlertType.SUCCESS, handler);
    };
  }

  onError(alerted: Alerted): Unsubscribe {
    const handler: Handler<AlertMessage> = message => alerted(message);
    this.emitter.on(AlertType.ERROR, handler);
    return () => {
      this.emitter.off(AlertType.ERROR, handler);
    };
  }
}
