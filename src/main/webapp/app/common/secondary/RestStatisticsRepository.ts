import { AxiosHttp } from '@/http/AxiosHttp';
import { Statistics } from '../domain/Statistics';
import { StatisticsRepository } from '../domain/StatisticsRepository';
import { RestStatistics, toStatistics } from './RestStatistics';

export class RestStatisticsRepository implements StatisticsRepository {
  constructor(private axiosInstance: AxiosHttp) {}

  get(): Promise<Statistics> {
    return this.axiosInstance.get<RestStatistics>('/api/statistics').then(toStatistics);
  }
}
