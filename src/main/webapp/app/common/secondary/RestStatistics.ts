import { Statistics } from '@/common/domain/Statistics';
import { AxiosResponse } from 'axios';

export interface RestStatistics {
  appliedModules: number;
}

export const toStatistics = (response: AxiosResponse<RestStatistics>): Statistics => {
  const { appliedModules } = response.data;
  return new Statistics(appliedModules);
};
