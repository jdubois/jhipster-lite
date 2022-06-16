import { flushPromises, shallowMount, VueWrapper } from '@vue/test-utils';
import { defaultModules, stubModulesRepository } from '../domain/Modules.fixture';
import { ModulesListVue } from '@/module/primary/modules-list';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import { wrappedElement } from '../../WrappedElement';

interface WrapperOptions {
  modules: ModulesRepository;
}

const wrap = (options: WrapperOptions): VueWrapper => {
  return shallowMount(ModulesListVue, {
    global: {
      provide: {
        modules: options.modules,
      },
    },
  });
};

describe('Modules list', () => {
  it('Should display loader when waiting for modules', () => {
    const modules = stubModulesRepository();
    modules.list.resolves(defaultModules());

    const wrapper = wrap({ modules });

    expect(wrapper.find(wrappedElement('modules-loader')).exists()).toBe(true);
    expect(wrapper.find(wrappedElement('modules-list')).exists()).toBe(false);
  });

  it('Should load modules at startup', async () => {
    const modules = stubModulesRepository();
    modules.list.resolves(defaultModules());
    const wrapper = wrap({ modules });

    await flushPromises();

    expect(wrapper.find(wrappedElement('modules-loader')).exists()).toBe(false);
    expect(wrapper.find(wrappedElement('modules-list')).exists()).toBe(true);
    expect(wrapper.find(wrappedElement('spring-cucumber-module')).exists()).toBe(true);
  });
});
