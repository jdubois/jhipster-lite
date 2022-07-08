import { flushPromises, mount, VueWrapper } from '@vue/test-utils';
import { defaultModules, ModulesRepositoryStub, stubModulesRepository } from '../domain/Modules.fixture';
import { ModulesVue } from '@/module/primary/modules';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import { wrappedElement } from '../../WrappedElement';
import { stubAlertBus } from '../../common/domain/AlertBus.fixture';

const alertBus = stubAlertBus();

interface WrapperOptions {
  modules: ModulesRepository;
}

const wrap = (options: WrapperOptions): VueWrapper => {
  return mount(ModulesVue, {
    global: {
      provide: {
        modules: options.modules,
        alertBus,
      },
    },
  });
};

describe('Modules', () => {
  it('Should display loader when waiting for modules', () => {
    const modules = repositoryWithModules();

    const wrapper = wrap({ modules });

    expect(wrapper.find(wrappedElement('modules-loader')).exists()).toBe(true);
    expect(wrapper.find(wrappedElement('modules-list')).exists()).toBe(false);
  });

  it('Should load modules at startup', async () => {
    const wrapper = await componentWithModules();

    expect(wrapper.find(wrappedElement('modules-loader')).exists()).toBe(false);
    expect(wrapper.find(wrappedElement('modules-list')).exists()).toBe(true);
    expect(wrapper.find(wrappedElement('spring-cucumber-module-content')).exists()).toBe(true);
  });

  it('Should select module', async () => {
    const wrapper = await componentWithModules();

    await selectModule(wrapper);

    expect(wrapper.find(wrappedElement('property-baseName-field')).attributes('type')).toBe('text');
    expect(wrapper.find(wrappedElement('property-baseName-optional-marker')).exists()).toBe(false);

    expect(wrapper.find(wrappedElement('property-optionalBoolean-field')).exists()).toBe(true);
    expect(wrapper.find(wrappedElement('property-optionalBoolean-optional-marker')).exists()).toBe(true);

    expect(wrapper.find(wrappedElement('property-optionalInteger-field')).attributes('type')).toBe('number');
    expect(wrapper.find(wrappedElement('property-optionalInteger-optional-marker')).exists()).toBe(true);
  });

  it('Should disable validation button without project folder', async () => {
    const wrapper = await componentWithModules();
    await selectModule(wrapper);

    wrapper.find(wrappedElement('property-baseName-field')).setValue('test');
    await flushForm(wrapper);

    expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).attributes('disabled')).toBeDefined();
  });

  it('Should disable validation button without mandatory fields', async () => {
    const wrapper = await componentWithModules();
    await selectModule(wrapper);

    wrapper.find(wrappedElement('folder-path-field')).setValue('test');
    await flushForm(wrapper);

    expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).attributes('disabled')).toBeDefined();
  });

  it('Should disable module application with empty string', async () => {
    const wrapper = await componentWithModules();
    await selectModule(wrapper);

    wrapper.find(wrappedElement('folder-path-field')).setValue('test');
    wrapper.find(wrappedElement('property-baseName-field')).setValue('');
    await flushForm(wrapper);

    expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).attributes('disabled')).toBeDefined();
  });

  it('Should enable module application with mandatory fields set', async () => {
    const wrapper = await componentWithModules();
    await selectModule(wrapper);

    wrapper.find(wrappedElement('folder-path-field')).setValue('test');
    wrapper.find(wrappedElement('property-baseName-field')).setValue('test');
    await flushForm(wrapper);

    expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).attributes('disabled')).toBeUndefined();
  });

  it('Should display setted properties', async () => {
    const wrapper = await componentWithModules();
    await selectModule(wrapper);

    wrapper.find(wrappedElement('folder-path-field')).setValue('test');
    wrapper.find(wrappedElement('property-baseName-field')).setValue('test');
    wrapper.find(wrappedElement('property-optionalBoolean-field')).setValue('true');
    wrapper.find(wrappedElement('property-optionalInteger-field')).setValue('42');
    await flushForm(wrapper);

    expect(wrapper.find(wrappedElement('module-spring-cucumber-baseName-property-value')).text()).toBe('test');
    expect(wrapper.find(wrappedElement('module-spring-cucumber-Mandatory-properties-stats')).text()).toBe('1 / 1');

    expect(wrapper.find(wrappedElement('module-spring-cucumber-optionalBoolean-property-value')).text()).toBe('true');
    expect(wrapper.find(wrappedElement('module-spring-cucumber-optionalInteger-property-value')).text()).toBe('42');
    expect(wrapper.find(wrappedElement('module-spring-cucumber-Optional-properties-stats')).text()).toBe('2 / 2');
  });

  it('Should set boolean property to false', async () => {
    const wrapper = await componentWithModules();
    await selectModule(wrapper);

    wrapper.find(wrappedElement('property-optionalBoolean-field')).setValue('false');
    await flushForm(wrapper);

    expect(wrapper.find(wrappedElement('module-spring-cucumber-optionalBoolean-property-value')).text()).toBe('false');
  });

  it('Should set boolean property to undefined', async () => {
    const wrapper = await componentWithModules();
    await selectModule(wrapper);

    wrapper.find(wrappedElement('property-optionalBoolean-field')).setValue('true');
    await flushForm(wrapper);
    wrapper.find(wrappedElement('property-optionalBoolean-field')).setValue('');
    await flushForm(wrapper);

    expect(wrapper.find(wrappedElement('module-spring-cucumber-optionalBoolean-property-value')).text()).toBe('');
  });

  it('Should set integer property to undefine', async () => {
    const wrapper = await componentWithModules();
    await selectModule(wrapper);

    wrapper.find(wrappedElement('property-optionalInteger-field')).setValue('42');
    await flushForm(wrapper);
    wrapper.find(wrappedElement('property-optionalInteger-field')).setValue('');
    await flushForm(wrapper);

    expect(wrapper.find(wrappedElement('module-spring-cucumber-optionalInteger-property-value')).text()).toBe('');
  });

  it('Should apply module using repository', async () => {
    const modules = repositoryWithModules();
    modules.apply.resolves(null);
    const wrapper = wrap({ modules });

    await flushPromises();
    await selectModule(wrapper);

    wrapper.find(wrappedElement('folder-path-field')).setValue('test');
    wrapper.find(wrappedElement('property-baseName-field')).setValue('test');
    await flushForm(wrapper);

    wrapper.find(wrappedElement('module-spring-cucumber-application-button')).trigger('click');

    await flushPromises();

    expect(modules.list.calledOnce).toBe(true);
  });

  it('Should disable applications during application', async () => {
    const modules = repositoryWithModules();
    modules.apply.returns(new Promise(resolve => setTimeout(resolve, 500)));
    const wrapper = await filledModuleForm(modules);

    wrapper.find(wrappedElement('module-spring-cucumber-application-button')).trigger('click');
    await flushForm(wrapper);

    expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).attributes('disabled')).toBeDefined();
  });

  it('Should enable applications after successfull application', async () => {
    const modules = repositoryWithModules();
    modules.apply.resolves(undefined);
    const wrapper = await filledModuleForm(modules);

    wrapper.find(wrappedElement('module-spring-cucumber-application-button')).trigger('click');
    await flushForm(wrapper);

    await flushPromises();

    expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).attributes('disabled')).toBeUndefined();
  });

  it('Should enable applications after failling application', async () => {
    const modules = repositoryWithModules();
    modules.apply.rejects('this is an error');
    const wrapper = await filledModuleForm(modules);

    wrapper.find(wrappedElement('module-spring-cucumber-application-button')).trigger('click');
    await flushForm(wrapper);

    await flushPromises();

    expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).attributes('disabled')).toBeUndefined();
  });

  it('Should send module application notification', async () => {
    const modules = repositoryWithModules();
    modules.apply.resolves(undefined);
    const wrapper = await filledModuleForm(modules);

    wrapper.find(wrappedElement('module-spring-cucumber-application-button')).trigger('click');
    await flushForm(wrapper);

    await flushPromises();

    const [message] = alertBus.success.getCall(0).args;
    expect(message).toBe('Module "spring-cucumber" applied');
  });

  it('Should send module application error notification', async () => {
    const modules = repositoryWithModules();
    modules.apply.rejects(undefined);
    const wrapper = await filledModuleForm(modules);

    wrapper.find(wrappedElement('module-spring-cucumber-application-button')).trigger('click');
    await flushForm(wrapper);

    await flushPromises();

    const [message] = alertBus.error.getCall(0).args;
    expect(message).toBe('Module "spring-cucumber" not applied');
  });

  it('Should filter modules with one matching slug module', async () => {
    const modules = repositoryWithModules();
    const wrapper = await filledModuleForm(modules);

    wrapper.find(wrappedElement('modules-filter-field')).setValue('spring-cucumber');
    await flushForm(wrapper);

    expect(wrapper.find(wrappedElement('module-banner-application-button')).exists()).toBe(false);
    expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).exists()).toBe(true);
  });

  it('Should filter modules with one matching description module', async () => {
    const modules = repositoryWithModules();
    const wrapper = await filledModuleForm(modules);

    wrapper.find(wrappedElement('modules-filter-field')).setValue('Add cucumber');
    await flushForm(wrapper);

    expect(wrapper.find(wrappedElement('module-banner-application-button')).exists()).toBe(false);
    expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).exists()).toBe(true);
  });

  it('Should filter modules with multiple words matching', async () => {
    const modules = repositoryWithModules();
    const wrapper = await filledModuleForm(modules);

    wrapper.find(wrappedElement('modules-filter-field')).setValue('spring-cucumber add application');
    await flushForm(wrapper);

    expect(wrapper.find(wrappedElement('module-banner-application-button')).exists()).toBe(false);
    expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).exists()).toBe(true);
  });

  it('Should filter modules with no maching module', async () => {
    const modules = repositoryWithModules();
    const wrapper = await filledModuleForm(modules);

    wrapper.find(wrappedElement('modules-filter-field')).setValue('pouet');
    await flushForm(wrapper);

    expect(wrapper.find(wrappedElement('module-banner-application-button')).exists()).toBe(false);
    expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).exists()).toBe(false);
  });

  it('Should mark already applied modules as applied', async () => {
    const modules = repositoryWithModules();
    modules.appliedModules.resolves(['spring-cucumber']);
    const wrapper = await filledModuleForm(modules);

    wrapper.find(wrappedElement('folder-path-field')).trigger('blur');

    await flushPromises();

    expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).text()).toBe('Re-apply');
  });

  it('Should reset modules application for unknown folder', async () => {
    const modules = repositoryWithModules();
    modules.appliedModules.rejects();
    modules.apply.resolves(undefined);

    const wrapper = await filledModuleForm(modules);
    wrapper.find(wrappedElement('module-spring-cucumber-application-button')).trigger('click');
    await flushForm(wrapper);
    await flushPromises();

    wrapper.find(wrappedElement('folder-path-field')).trigger('blur');
    await flushPromises();

    expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).text()).toBe('Apply');
  });

  it('Should mark modules as applied after application', async () => {
    const modules = repositoryWithModules();
    modules.apply.resolves(undefined);
    const wrapper = await filledModuleForm(modules);

    wrapper.find(wrappedElement('module-spring-cucumber-application-button')).trigger('click');
    await flushForm(wrapper);

    await flushPromises();

    expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).text()).toBe('Re-apply');
  });
});

const componentWithModules = async (): Promise<VueWrapper> => {
  const modules = repositoryWithModules();
  const wrapper = wrap({ modules });

  await flushPromises();

  return wrapper;
};

const selectModule = async (wrapper: VueWrapper) => {
  wrapper.find(wrappedElement('spring-cucumber-module-content')).trigger('click');
  await wrapper.vm.$nextTick();
};

const flushForm = async (wrapper: VueWrapper) => {
  await wrapper.vm.$nextTick();
};

const filledModuleForm = async (modules: ModulesRepository): Promise<VueWrapper> => {
  const wrapper = wrap({ modules });

  await flushPromises();
  await selectModule(wrapper);

  wrapper.find(wrappedElement('folder-path-field')).setValue('test');
  wrapper.find(wrappedElement('property-baseName-field')).setValue('test');
  await flushForm(wrapper);

  return wrapper;
};

const repositoryWithModules = (): ModulesRepositoryStub => {
  const modules = stubModulesRepository();
  modules.list.resolves(defaultModules());

  return modules;
};
