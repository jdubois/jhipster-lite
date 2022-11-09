import { RestStatistics } from '@/common/secondary/RestStatistics';
import { RestStatisticsRepository } from '@/common/secondary/RestStatisticsRepository';
import { dataBackendResponse, stubAxiosHttp } from '../../http/AxiosHttpStub';
import { describe, it, expect } from 'vitest';

describe('Rest statistics repository', () => {
  it('Should get statistics using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestStatisticsRepository(axiosInstance);
    axiosInstance.get.resolves(dataBackendResponse(restStatistics()));

    const statistics = await repository.get();

    expect(statistics.get()).toEqual(1000);
  });
});

const restStatistics = (): RestStatistics => ({ appliedModules: 1000 });
