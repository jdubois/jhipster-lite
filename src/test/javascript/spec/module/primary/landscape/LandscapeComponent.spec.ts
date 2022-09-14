import { ApplicationListener } from '@/common/primary/applicationlistener/ApplicationListener';
import { ModuleSlug } from '@/module/domain/ModuleSlug';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import { ModulesToApply } from '@/module/domain/ModulesToApply';
import { LandscapeVue } from '@/module/primary/landscape';
import { flushPromises, mount, VueWrapper } from '@vue/test-utils';
import sinon, { SinonStub } from 'sinon';
import { stubAlertBus } from '../../../common/domain/AlertBus.fixture';
import { wrappedElement } from '../../../WrappedElement';
import { defaultLandscape } from '../../domain/landscape/Landscape.fixture';
import { ModulesRepositoryStub, projectHistoryWithInit, stubModulesRepository } from '../../domain/Modules.fixture';
import { ProjectFoldersRepositoryStub, stubProjectFoldersRepository } from '../../domain/ProjectFolders.fixture';
import { stubWindow } from '../GlobalWindow.fixture';

interface ApplicationListenerStub extends ApplicationListener {
  addEventListener: SinonStub;
  removeEventListener: SinonStub;
}

const stubApplicationListener = (): ApplicationListenerStub => ({
  addEventListener: sinon.stub(),
  removeEventListener: sinon.stub(),
});

interface WrapperOptions {
  modules: ModulesRepository;
  applicationListener: ApplicationListener;
}

const alertBus = stubAlertBus();

const wrap = (options?: Partial<WrapperOptions>): VueWrapper => {
  const { modules, applicationListener }: WrapperOptions = {
    modules: repositoryWithLandscape(),
    applicationListener: stubApplicationListener(),
    ...options,
  };

  return mount(LandscapeVue, {
    global: {
      provide: {
        modules,
        applicationListener,
        alertBus,
        projectFolders: repositoryWithProjectFolders(),
        globalWindow: stubWindow(),
      },
    },
  });
};

const componentWithLandscape = async (applicationListener?: ApplicationListener): Promise<VueWrapper> => {
  const listener = applicationListener || stubApplicationListener();
  const modules = repositoryWithLandscape();

  const wrapper = wrap({ modules, applicationListener: listener });

  await flushPromises();

  return wrapper;
};

const repositoryWithLandscape = (): ModulesRepositoryStub => {
  const modules = stubModulesRepository();
  modules.landscape.resolves(defaultLandscape());
  modules.applyAll.resolves(undefined);
  modules.history.resolves(projectHistoryWithInit());

  return modules;
};

const repositoryWithProjectFolders = (): ProjectFoldersRepositoryStub => {
  const projectFolders = stubProjectFoldersRepository();
  projectFolders.get.resolves('/tmp/jhlite/1234');

  return projectFolders;
};

describe('Landscape', () => {
  describe('Loading', () => {
    it('Should display loader when loading landscape', () => {
      const wrapper = wrap();

      expect(wrapper.find(wrappedElement('landscape-loader')).exists()).toBe(true);
      expect(wrapper.find(wrappedElement('landscape')).exists()).toBe(false);
    });

    it('Should load landscape at startup', async () => {
      const applicationListener = stubApplicationListener();
      const wrapper = await componentWithLandscape(applicationListener);

      expect(wrapper.find(wrappedElement('landscape-loader')).exists()).toBe(false);
      expect(wrapper.find(wrappedElement('landscape')).exists()).toBe(true);
      expect(wrapper.find(wrappedElement('landscape-connectors')).findAll('polyline').length).toBe(17);
      expect(applicationListener.addEventListener.calledOnce).toBe(true);

      const pathField = wrapper.find(wrappedElement('folder-path-field')).element as HTMLInputElement;
      expect(pathField.value).toBe('/tmp/jhlite/1234');
    });

    it('Should unload landscape at destroy', async () => {
      const applicationListener = stubApplicationListener();
      const wrapper = await componentWithLandscape(applicationListener);

      wrapper.unmount();

      expect(applicationListener.removeEventListener.calledOnce).toBe(true);
    });
  });

  describe('Display modes', () => {
    it('Should switch to compacted mode', async () => {
      const wrapper = await componentWithLandscape();

      wrapper.find(wrappedElement('compacted-mode-button')).trigger('click');
      await wrapper.vm.$nextTick();

      expect(wrapper.find(wrappedElement('compacted-mode-button')).classes()).toContain('-selected');
      expect(wrapper.find(wrappedElement('extended-mode-button')).classes()).toContain('-not-selected');
      expect(wrapper.find(wrappedElement('infinitest-module')).classes()).toContain('-compacted');
    });

    it('Should switch to extended mode', async () => {
      const wrapper = await componentWithLandscape();

      wrapper.find(wrappedElement('extended-mode-button')).trigger('click');
      await wrapper.vm.$nextTick();

      expect(wrapper.find(wrappedElement('compacted-mode-button')).classes()).toContain('-not-selected');
      expect(wrapper.find(wrappedElement('extended-mode-button')).classes()).toContain('-selected');
      expect(wrapper.find(wrappedElement('infinitest-module')).classes()).toContain('-extended');
    });
  });

  describe('Modules emphasize', () => {
    it('Should highlight compacted selectable module and dependencies', async () => {
      const wrapper = await componentWithLandscape();

      wrapper.find(wrappedElement('vue-module')).trigger('mouseover');
      await wrapper.vm.$nextTick();

      const vueClasses = wrapper.find(wrappedElement('vue-module')).classes();
      expect(vueClasses).toContain('-selectable-highlighted');
      expect(vueClasses).toContain('-compacted');

      const initClasses = wrapper.find(wrappedElement('init-module')).classes();
      expect(initClasses).toContain('-selectable-highlighted');
      expect(initClasses).toContain('-compacted');

      assertSelectableHighlightedConnectorsCount(wrapper, 1);
    });

    it('Should highlight extended selectable module and dependencies', async () => {
      const wrapper = await componentWithLandscape();
      wrapper.find(wrappedElement('extended-mode-button')).trigger('click');
      await wrapper.vm.$nextTick();

      wrapper.find(wrappedElement('vue-module')).trigger('mouseover');
      await wrapper.vm.$nextTick();

      const vueClasses = wrapper.find(wrappedElement('vue-module')).classes();
      expect(vueClasses).toContain('-selectable-highlighted');
      expect(vueClasses).toContain('-extended');

      const initClasses = wrapper.find(wrappedElement('init-module')).classes();
      expect(initClasses).toContain('-selectable-highlighted');
      expect(initClasses).toContain('-extended');

      assertSelectableHighlightedConnectorsCount(wrapper, 1);
    });

    it('Should highlight dependant feature', async () => {
      const wrapper = await componentWithLandscape();

      wrapper.find(wrappedElement('dummy-feature-module')).trigger('mouseover');
      await wrapper.vm.$nextTick();

      const dummyFeatureClasses = wrapper.find(wrappedElement('dummy-feature-module')).classes();
      expect(dummyFeatureClasses).toContain('-not-selectable-highlighted');
      expect(dummyFeatureClasses).toContain('-compacted');

      const springMvcClasses = wrapper.find(wrappedElement('spring-mvc-feature')).classes();
      expect(springMvcClasses).toContain('-not-selectable-highlighted');
      expect(springMvcClasses).toContain('-compacted');
      assertNotSelectableHighlightedConnectorsCount(wrapper, 2);
    });

    it('Should highlight selection tree', async () => {
      const wrapper = await componentWithLandscape();

      await clickModule('maven', wrapper);

      wrapper.find(wrappedElement('liquibase-module')).trigger('mouseover');
      await wrapper.vm.$nextTick();

      expect(wrapper.find(wrappedElement('java-build-tools-feature')).classes()).toContain('-selectable-highlighted');
      expect(wrapper.find(wrappedElement('spring-boot-module')).classes()).toContain('-selectable-highlighted');
      expect(wrapper.find(wrappedElement('postgresql-module')).classes()).toContain('-selectable-highlighted');
      expect(wrapper.find(wrappedElement('liquibase-module')).classes()).toContain('-selectable-highlighted');
      assertSelectableHighlightedConnectorsCount(wrapper, 4);
    });

    it('Should un-highlight module and dependencies', async () => {
      const wrapper = await componentWithLandscape();

      wrapper.find(wrappedElement('vue-module')).trigger('mouseover');
      await wrapper.vm.$nextTick();
      wrapper.find(wrappedElement('vue-module')).trigger('mouseleave');
      await wrapper.vm.$nextTick();

      expect(wrapper.find(wrappedElement('vue-module')).classes()).not.toContain('-selectable-highlighted');
      expect(wrapper.find(wrappedElement('init-module')).classes()).not.toContain('-selectable-highlighted');
      assertSelectableHighlightedConnectorsCount(wrapper, 0);
    });

    it('Should highlight unselection tree', async () => {
      const wrapper = await componentWithLandscape();

      await clickModule('maven', wrapper);
      await clickModule('spring-boot', wrapper);

      wrapper.find(wrappedElement('init-module')).trigger('mouseover');
      await wrapper.vm.$nextTick();

      expect(wrapper.find(wrappedElement('maven-module')).classes()).toContain('-highlighted-unselection');
      expect(wrapper.find(wrappedElement('spring-boot-module')).classes()).toContain('-highlighted-unselection');
      assertUnSelectionHighlightedConnectorsCount(wrapper, 2);
    });
  });

  describe('Selectable module', () => {
    it('Should mark compacted root modules as selectable', async () => {
      const wrapper = await componentWithLandscape();

      const initClasses = wrapper.find(wrappedElement('init-module')).classes();
      expect(initClasses).toContain('-selectable');
      expect(initClasses).toContain('-compacted');

      const infinitestClasses = wrapper.find(wrappedElement('infinitest-module')).classes();
      expect(infinitestClasses).toContain('-selectable');
      expect(infinitestClasses).toContain('-compacted');
    });

    it('Should mark extended root modules as selectable', async () => {
      const wrapper = await componentWithLandscape();
      wrapper.find(wrappedElement('extended-mode-button')).trigger('click');
      await wrapper.vm.$nextTick();

      const initClasses = wrapper.find(wrappedElement('init-module')).classes();
      expect(initClasses).toContain('-selectable');
      expect(initClasses).toContain('-extended');

      const infinitestClasses = wrapper.find(wrappedElement('infinitest-module')).classes();
      expect(infinitestClasses).toContain('-selectable');
      expect(infinitestClasses).toContain('-extended');
    });

    it('Should mark feature depending modules as not selectable', async () => {
      const wrapper = await componentWithLandscape();

      expect(wrapper.find(wrappedElement('java-base-module')).classes()).toContain('-not-selectable');
    });
  });

  describe('Module selection', () => {
    it('Should select selectable module', async () => {
      const wrapper = await componentWithLandscape();

      await clickModule('init', wrapper);

      const initClasses = wrapper.find(wrappedElement('init-module')).classes();
      expect(initClasses).toContain('-selected');
      expect(initClasses).not.toContain('-selectable');
      expect(initClasses).not.toContain('-not-selectable');

      assertSelectedConnectorsCount(wrapper, 0);
      expect(wrapper.find(wrappedElement('modules-apply-new-button')).text()).toContain('(1)');
      expect(wrapper.find(wrappedElement('modules-apply-all-button')).text()).toContain('(1)');
    });

    it('Should select module in feature', async () => {
      const wrapper = await componentWithLandscape();
      await updatePath(wrapper);

      await clickModule('init', wrapper);
      await clickModule('maven', wrapper);

      assertSelectedConnectorsCount(wrapper, 1);
      expect(wrapper.find(wrappedElement('modules-apply-new-button')).text()).toContain('(1)');
      expect(wrapper.find(wrappedElement('modules-apply-all-button')).text()).toContain('(2)');
    });

    it('Should not select not selectable module', async () => {
      const wrapper = await componentWithLandscape();

      await clickModule('java-base', wrapper);

      expect(wrapper.find(wrappedElement('java-base-module')).classes()).not.toContain('-selected');
    });
  });

  describe('Module unselection', () => {
    it('Should unselect selected module', async () => {
      const wrapper = await componentWithLandscape();

      await clickModule('init', wrapper);
      await clickModule('init', wrapper);

      expect(wrapper.find(wrappedElement('init-module')).classes()).not.toContain('-selected');
      expect(wrapper.find(wrappedElement('modules-apply-new-button')).text()).toContain('(0)');
      expect(wrapper.find(wrappedElement('modules-apply-all-button')).text()).toContain('(0)');
    });

    it('Should unselect dependant modules', async () => {
      const wrapper = await componentWithLandscape();

      await clickModule('init', wrapper);
      await clickModule('maven', wrapper);
      await clickModule('java-base', wrapper);
      await clickModule('init', wrapper);

      expect(wrapper.find(wrappedElement('init-module')).classes()).not.toContain('-selected');
      expect(wrapper.find(wrappedElement('maven-module')).classes()).not.toContain('-selected');
      expect(wrapper.find(wrappedElement('java-base-module')).classes()).not.toContain('-selected');

      expect(wrapper.find(wrappedElement('modules-apply-new-button')).text()).toContain('(0)');
      expect(wrapper.find(wrappedElement('modules-apply-all-button')).text()).toContain('(0)');
    });
  });

  describe('New modules application', () => {
    it('Should disable application button without selected modules', async () => {
      const wrapper = await componentWithLandscape();

      await wrapper.find(wrappedElement('folder-path-field')).setValue('test');

      expect(wrapper.find(wrappedElement('modules-apply-new-button')).attributes('disabled')).toBeDefined();
    });

    it('Should disable application button without project path', async () => {
      const wrapper = await componentWithLandscape();

      await clickModule('maven', wrapper);
      await wrapper.find(wrappedElement('folder-path-field')).setValue('');

      expect(wrapper.find(wrappedElement('modules-apply-new-button')).attributes('disabled')).toBeDefined();
    });

    it('Should disable application button with missing mandatory properties', async () => {
      const wrapper = await componentWithLandscape();

      await wrapper.find(wrappedElement('folder-path-field')).setValue('test');
      await clickModule('init', wrapper);

      expect(wrapper.find(wrappedElement('modules-apply-new-button')).attributes('disabled')).toBeDefined();
    });

    it('Should apply module using repository', async () => {
      const modules = repositoryWithLandscape();
      const wrapper = wrap({ modules });
      await flushPromises();

      await updatePath(wrapper);
      await clickModule('vue', wrapper);

      await wrapper.find(wrappedElement('modules-apply-new-button')).trigger('click');

      await flushPromises();

      const [appliedModules] = modules.applyAll.lastCall.args as ModulesToApply[];
      expect(appliedModules.modules.map(slug => slug.get())).toEqual(['vue']);
      expect(wrapper.find(wrappedElement('modules-apply-new-button')).attributes('disabled')).toBeDefined();

      const [message] = alertBus.success.lastCall.args;
      expect(message).toBe('Modules applied');
    });
  });

  describe('All modules application', () => {
    it('Should disable application button without selected modules', async () => {
      const wrapper = await componentWithLandscape();

      await wrapper.find(wrappedElement('folder-path-field')).setValue('test');

      expect(wrapper.find(wrappedElement('modules-apply-all-button')).attributes('disabled')).toBeDefined();
    });

    it('Should disable application button without project path', async () => {
      const wrapper = await componentWithLandscape();

      await clickModule('maven', wrapper);
      await wrapper.find(wrappedElement('folder-path-field')).setValue('');

      expect(wrapper.find(wrappedElement('modules-apply-all-button')).attributes('disabled')).toBeDefined();
    });

    it('Should disable application button with missing mandatory properties', async () => {
      const wrapper = await componentWithLandscape();

      await wrapper.find(wrappedElement('folder-path-field')).setValue('test');
      await clickModule('init', wrapper);

      expect(wrapper.find(wrappedElement('modules-apply-all-button')).attributes('disabled')).toBeDefined();
    });

    it('Should apply module using repository', async () => {
      const modules = repositoryWithLandscape();
      const wrapper = wrap({ modules });
      await flushPromises();

      await wrapper.find(wrappedElement('folder-path-field')).setValue('test');
      await clickModule('init', wrapper);
      await clickModule('vue', wrapper);
      await wrapper.find(wrappedElement('parameter-baseName-field')).setValue('base');

      await wrapper.find(wrappedElement('modules-apply-all-button')).trigger('click');

      await flushPromises();

      const [appliedModules] = modules.applyAll.lastCall.args as ModulesToApply[];
      expect(appliedModules.modules.map(slug => slug.get())).toEqual(['init', 'vue']);
      expect(wrapper.find(wrappedElement('modules-apply-all-button')).attributes('disabled')).toBeUndefined();

      const initClasses = wrapper.find(wrappedElement('init-module')).classes();
      expect(initClasses).toContain('-selected');
      expect(initClasses).toContain('-applied');

      const [message] = alertBus.success.lastCall.args;
      expect(message).toBe('Modules applied');
    });

    it('Should remove setted boolean parameter', async () => {
      const wrapper = await componentWithLandscape();

      await clickModule('maven', wrapper);
      await wrapper.find(wrappedElement('parameter-optionalBoolean-field')).setValue('true');
      await wrapper.find(wrappedElement('parameter-optionalBoolean-field')).setValue('');

      expect(wrapper.find(wrappedElement('modules-apply-all-button')).attributes('disabled')).toBeDefined();
    });

    it('Should apply modules without committing them', async () => {
      const modules = repositoryWithLandscape();
      const wrapper = wrap({ modules });
      await flushPromises();

      await wrapper.find(wrappedElement('folder-path-field')).setValue('test');
      await clickModule('init', wrapper);
      await wrapper.find(wrappedElement('parameter-baseName-field')).setValue('base');
      await wrapper.find(wrappedElement('commit-module-application')).trigger('click');

      wrapper.find(wrappedElement('modules-apply-all-button')).trigger('click');

      await flushPromises();

      const [modulesToApply] = modules.applyAll.firstCall.args;
      expect(modulesToApply.commit).toBe(false);
    });

    it('Should handle application error', async () => {
      const modules = repositoryWithLandscape();
      modules.applyAll.rejects();
      const wrapper = wrap({ modules });
      await flushPromises();

      await validateInitApplication(wrapper);

      expect(modules.applyAll.calledOnce).toBe(true);
      expect(wrapper.find(wrappedElement('modules-apply-all-button')).attributes('disabled')).toBeUndefined();

      const [message] = alertBus.error.lastCall.args;
      expect(message).toBe('Modules not applied');
    });

    it('Should disable actions during application', async () => {
      const modules = repositoryWithLandscape();
      modules.applyAll.returns(new Promise(resolve => setTimeout(resolve, 500)));
      const wrapper = wrap({ modules });
      await flushPromises();

      await validateInitApplication(wrapper);

      expect(wrapper.find(wrappedElement('modules-apply-all-button')).attributes('disabled')).toBeDefined();
      expect(wrapper.find(wrappedElement('init-module')).classes()).toContain('-not-selectable');
    });
  });

  describe('History', () => {
    it('Should load information from history', async () => {
      const modules = repositoryWithLandscape();
      const wrapper = wrap({ modules });
      await flushPromises();

      await updatePath(wrapper);

      const baseNameField = wrapper.find(wrappedElement('parameter-baseName-field')).element as HTMLInputElement;
      expect(baseNameField.value).toBe('settedbase');

      const initClasses = wrapper.find(wrappedElement('init-module')).classes();
      expect(initClasses).toContain('-selected');
      expect(initClasses).toContain('-applied');
    });

    it('Should silently handle history loading error', async () => {
      const modules = repositoryWithLandscape();
      modules.history.rejects(undefined);
      const wrapper = wrap({ modules });
      await flushPromises();

      const consoleErrors = jest.spyOn(console, 'error').mockImplementation();
      await updatePath(wrapper);

      expect(console.error).toHaveBeenCalledTimes(0);
      consoleErrors.mockRestore();
    });

    it('Should ignore unknown modules from history', async () => {
      const modules = repositoryWithLandscape();
      modules.history.resolves({
        modules: [new ModuleSlug('unknown')],
        properties: [],
      });
      const wrapper = wrap({ modules });
      await flushPromises();
      await updatePath(wrapper);

      expect(wrapper.find(wrappedElement('init-module')).exists()).toBe(true);
    });

    it('Should not replace user setted properties from history', async () => {
      const modules = repositoryWithLandscape();
      const wrapper = wrap({ modules });
      await flushPromises();

      await clickModule('init', wrapper);
      await wrapper.find(wrappedElement('parameter-baseName-field')).setValue('pouet');
      await updatePath(wrapper);

      const baseNameField = wrapper.find(wrappedElement('parameter-baseName-field')).element as HTMLInputElement;
      expect(baseNameField.value).toBe('pouet');
    });
  });

  describe('Formatting', () => {
    it('Should disable applications during project formatting', async () => {
      const modules = repositoryWithLandscape();
      modules.format.returns(new Promise(resolve => setTimeout(resolve, 500)));
      const wrapper = wrap({ modules });
      await flushPromises();

      await updatePath(wrapper);
      await wrapper.find(wrappedElement('format-button')).trigger('click');

      expect(wrapper.find(wrappedElement('modules-apply-all-button')).attributes('disabled')).toBeDefined();
      expect(wrapper.find(wrappedElement('init-module')).classes()).toContain('-compacted');
    });

    it('Should enable applications after project formatting', async () => {
      const modules = repositoryWithLandscape();
      modules.format.resolves(undefined);
      const wrapper = wrap({ modules });
      await flushPromises();

      await updatePath(wrapper);
      await wrapper.find(wrappedElement('format-button')).trigger('click');
      await flushPromises();

      expect(wrapper.find(wrappedElement('modules-apply-all-button')).attributes('disabled')).toBeUndefined();
    });
  });

  describe('Download', () => {
    it('Should disable applications during download', async () => {
      const modules = repositoryWithLandscape();
      modules.download.returns(new Promise(resolve => setTimeout(resolve, 500)));
      const wrapper = wrap({ modules });
      await flushPromises();

      await updatePath(wrapper);
      await wrapper.find(wrappedElement('download-button')).trigger('click');

      expect(wrapper.find(wrappedElement('modules-apply-all-button')).attributes('disabled')).toBeDefined();
    });

    it('Should enable applications after download', async () => {
      const modules = repositoryWithLandscape();
      modules.download.resolves(undefined);
      const wrapper = wrap({ modules });
      await flushPromises();

      await updatePath(wrapper);
      wrapper.find(wrappedElement('download-button')).trigger('click');
      await flushPromises();

      expect(wrapper.find(wrappedElement('modules-apply-all-button')).attributes('disabled')).toBeUndefined();
    });
  });
});

const assertSelectableHighlightedConnectorsCount = (wrapper: VueWrapper, count: number): void => {
  assertConnectorsCount(wrapper, '-selectable-highlighted', count);
};

const assertNotSelectableHighlightedConnectorsCount = (wrapper: VueWrapper, count: number): void => {
  assertConnectorsCount(wrapper, '-not-selectable-highlighted', count);
};

const assertUnSelectionHighlightedConnectorsCount = (wrapper: VueWrapper, count: number): void => {
  assertConnectorsCount(wrapper, '-highlighted-unselection', count);
};

const assertSelectedConnectorsCount = (wrapper: VueWrapper, count: number): void => {
  assertConnectorsCount(wrapper, '-selected', count);
};

const assertConnectorsCount = (wrapper: VueWrapper, cssClass: string, count: number): void => {
  expect(
    wrapper
      .find(wrappedElement('landscape-connectors'))
      .findAll('polyline')
      .filter(line => line.classes().includes(cssClass)).length
  ).toBe(count);
};

const validateInitApplication = async (wrapper: VueWrapper): Promise<void> => {
  await wrapper.find(wrappedElement('folder-path-field')).setValue('test');
  await clickModule('init', wrapper);
  await wrapper.find(wrappedElement('parameter-baseName-field')).setValue('base');

  await wrapper.find(wrappedElement('modules-apply-all-button')).trigger('click');

  await flushPromises();
};

const clickModule = async (slug: string, wrapper: VueWrapper): Promise<void> => {
  await wrapper.find(wrappedElement(`${slug}-module`)).trigger('click');
};

const updatePath = async (wrapper: VueWrapper): Promise<void> => {
  await wrapper.find(wrappedElement('folder-path-field')).setValue('test');
  await wrapper.find(wrappedElement('folder-path-field')).trigger('blur');
};
