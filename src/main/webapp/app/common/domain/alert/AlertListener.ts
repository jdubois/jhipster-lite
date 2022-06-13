import { Alerted } from '@/common/domain/alert/Alerted';
import { Unsubscribe } from '@/common/domain/alert/Unsubscribe';

export interface AlertListener {
  onSuccess(alerted: Alerted): Unsubscribe;
  onError(alerted: Alerted): Unsubscribe;
}
