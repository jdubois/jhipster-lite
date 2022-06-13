import { AlertMessage } from '@/common/domain/alert/AlertMessage';

export type Alerted = (message: AlertMessage) => void;
