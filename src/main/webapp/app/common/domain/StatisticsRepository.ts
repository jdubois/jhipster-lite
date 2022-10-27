import { Statistics } from '@/common/domain/Statistics';

export interface StatisticsRepository {
  get(): Promise<Statistics>;
}
