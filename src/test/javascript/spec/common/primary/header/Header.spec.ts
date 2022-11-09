import { Statistics } from '@/common/domain/Statistics';
import { StatisticsRepository } from '@/common/domain/StatisticsRepository';
import { HeaderVue } from '@/common/primary/header';
import { flushPromises, shallowMount, VueWrapper } from '@vue/test-utils';
import sinon, { SinonStub } from 'sinon';
import { wrappedElement } from '../../../WrappedElement';
import { describe, it, expect } from 'vitest';

export interface StatisticsRepositoryStub extends StatisticsRepository {
  get: SinonStub;
}

export const stubStatisticsRepository = (): StatisticsRepositoryStub => ({ get: sinon.stub() } as StatisticsRepositoryStub);

interface WrapperOptions {
  statistics: StatisticsRepository;
}

const wrap = (options?: Partial<WrapperOptions>): VueWrapper => {
  const { statistics }: WrapperOptions = {
    statistics: repositoryWithStatistics(),
    ...options,
  };

  return shallowMount(HeaderVue, {
    global: {
      stubs: ['router-link'],
      provide: {
        statistics,
      },
    },
  });
};

describe('Header', () => {
  it('should exist', () => {
    const wrapper = wrap();

    expect(wrapper.exists()).toBe(true);
  });

  it('should load statistics', async () => {
    const wrapper = wrap();
    await flushPromises();

    expect(wrapper.find(wrappedElement('statistics')).exists()).toBe(true);
  });
});

const repositoryWithStatistics = (): StatisticsRepositoryStub => {
  const statistics = stubStatisticsRepository();
  statistics.get.resolves(new Statistics(10000));

  return statistics;
};
