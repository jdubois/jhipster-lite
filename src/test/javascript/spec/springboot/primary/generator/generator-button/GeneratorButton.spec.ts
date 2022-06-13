import { shallowMount, VueWrapper } from '@vue/test-utils';
import { GeneratorButtonVue } from '@/springboot/primary/generator/generator-button';
import { createPinia, setActivePinia, StoreGeneric } from 'pinia';
import { useHistoryStore } from '@/common/primary/HistoryStore';
import { createHistory } from '../../../../common/domain/History.fixture';
import { Service } from '@/common/domain/Service';
import { ServiceProjection } from '@/springboot/primary/generator/ServiceProjection';

let wrapper: VueWrapper;
let component: any;

interface WrapperOptions {
  historyStore: StoreGeneric;
  service: ServiceProjection;
}

const wrap = (wrapperOptions?: Partial<WrapperOptions>) => {
  const { historyStore, service }: WrapperOptions = {
    historyStore: useHistoryStore(),
    service: 'initialization',
    ...wrapperOptions,
  };
  wrapper = shallowMount(GeneratorButtonVue, {
    props: {
      label: 'Init',
      service: service,
      selectorPrefix: 'prefix',
    },
    global: {
      provide: {
        historyStore,
      },
    },
  });
  component = wrapper.vm;
};

describe('GeneratorButton', () => {
  beforeEach(() => {
    const pinia = createPinia();
    setActivePinia(pinia);
  });

  it('should exist', () => {
    wrap();

    expect(wrapper.exists()).toBe(true);
  });

  it('should have called service', () => {
    const historyStore = useHistoryStore();
    historyStore.setHistory(
      createHistory({
        services: [Service.INITIALIZATION],
      })
    );
    wrap({
      historyStore,
      service: 'initialization',
    });

    expect(component.hasCalledService).toBe(true);
  });

  it('should not have called service', () => {
    const historyStore = useHistoryStore();
    historyStore.setHistory(
      createHistory({
        services: [Service.INITIALIZATION],
      })
    );
    wrap({
      historyStore,
      service: 'java-base',
    });

    expect(component.hasCalledService).toBe(false);
  });
});
