import { flushPromises, mount, VueWrapper } from '@vue/test-utils';
import { defaultModules, defaultProject, moduleHistory, ModulesRepositoryStub, stubModulesRepository } from '../domain/Modules.fixture';
import { ModulesVue } from '@/module/primary/modules';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import { wrappedElement } from '../../WrappedElement';
import { stubAlertBus } from '../../common/domain/AlertBus.fixture';
import { ProjectFoldersRepository } from '@/module/domain/ProjectFoldersRepository';
import { ProjectFoldersRepositoryStub, stubProjectFoldersRepository } from '../domain/ProjectFolders.fixture';
import sinon from 'sinon';

const stubWindow = () => ({
  URL: { createObjectURL: sinon.stub(), revokeObjectURL: sinon.stub() },
  document: { createElement: sinon.stub(), body: { appendChild: sinon.stub(), removeChild: sinon.stub() } },
});

const stubLink = () => ({
  href: '',
  click: sinon.stub(),
  download: '',
});

interface WrapperOptions {
  modules: ModulesRepository;
  projectFolders: ProjectFoldersRepository;
}

const alertBus = stubAlertBus();

const windowStub = stubWindow();

const wrap = (options?: Partial<WrapperOptions>): VueWrapper => {
  const { modules, projectFolders }: WrapperOptions = {
    modules: repositoryWithModules(),
    projectFolders: repositoryWithProjectFolders(),
    ...options,
  };
  return mount(ModulesVue, {
    global: {
      provide: {
        modules,
        projectFolders,
        alertBus,
        globalWindow: windowStub,
      },
    },
  });
};

describe('Modules', () => {
  describe('Loading', () => {
    it('Should display loader when waiting for modules', () => {
      const wrapper = wrap();

      expect(wrapper.find(wrappedElement('modules-loader')).exists()).toBe(true);
      expect(wrapper.find(wrappedElement('modules-list')).exists()).toBe(false);
    });

    it('Should load modules at startup', async () => {
      const wrapper = await componentWithModules();

      expect(wrapper.find(wrappedElement('modules-loader')).exists()).toBe(false);
      expect(wrapper.find(wrappedElement('modules-list')).exists()).toBe(true);
      expect(wrapper.find(wrappedElement('spring-cucumber-module-content')).exists()).toBe(true);
      expect(wrapper.find(wrappedElement('banner-module-content')).classes()).toEqual([
        'jhipster-module--content',
        'not-selected',
        'not-applied',
      ]);
      expect(wrapper.find(wrappedElement('module-spring-cucumber-MANDATORY-properties')).classes()).toEqual([
        'jhipster-module-properties',
        'not-selected',
        'invalid-mandatory-property',
      ]);
      expect(wrapper.find(wrappedElement('module-spring-cucumber-OPTIONAL-properties')).classes()).toEqual([
        'jhipster-module-properties',
        'not-selected',
        'invalid-optional-property',
      ]);

      const pathField = wrapper.find(wrappedElement('folder-path-field')).element as HTMLInputElement;
      expect(pathField.value).toBe('/tmp/jhlite/1234');
    });
  });

  describe('Properties filling', () => {
    it('Should select module', async () => {
      const wrapper = await componentWithModules();

      await selectModule(wrapper);

      expect(wrapper.find(wrappedElement('property-baseName-field')).attributes('type')).toBe('text');
      expect(wrapper.find(wrappedElement('property-baseName-optional-marker')).exists()).toBe(false);

      expect(wrapper.find(wrappedElement('property-optionalBoolean-field')).exists()).toBe(true);
      expect(wrapper.find(wrappedElement('property-optionalBoolean-optional-marker')).exists()).toBe(true);

      expect(wrapper.find(wrappedElement('property-optionalInteger-field')).attributes('type')).toBe('number');
      expect(wrapper.find(wrappedElement('property-optionalInteger-optional-marker')).exists()).toBe(true);

      expect(wrapper.find(wrappedElement('banner-module-content')).classes()).toEqual([
        'jhipster-module--content',
        'not-selected',
        'not-applied',
      ]);
      expect(wrapper.find(wrappedElement('spring-cucumber-module-content')).classes()).toEqual([
        'jhipster-module--content',
        'selected',
        'not-applied',
      ]);
    });

    it('Should de-select module', async () => {
      const wrapper = await componentWithModules();

      await selectModule(wrapper);
      wrapper.find(wrappedElement('spring-cucumber-module-content')).trigger('click');
      await wrapper.vm.$nextTick();

      expect(wrapper.find(wrappedElement('property-baseName-field')).exists()).toBe(false);
      expect(wrapper.find(wrappedElement('property-optionalBoolean-field')).exists()).toBe(false);
      expect(wrapper.find(wrappedElement('property-optionalInteger-field')).exists()).toBe(false);
      expect(wrapper.find(wrappedElement('spring-cucumber-module-content')).classes()).toEqual([
        'jhipster-module--content',
        'not-selected',
        'not-applied',
      ]);
    });

    it('Should disable validation button without project folder', async () => {
      const wrapper = await componentWithModules();
      await selectModule(wrapper);

      wrapper.find(wrappedElement('folder-path-field')).setValue('');
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
      expect(wrapper.find(wrappedElement('module-spring-cucumber-MANDATORY-properties-stats')).text()).toBe('1 / 1');

      expect(wrapper.find(wrappedElement('module-spring-cucumber-optionalBoolean-property-value')).text()).toBe('true');
      expect(wrapper.find(wrappedElement('module-spring-cucumber-optionalInteger-property-value')).text()).toBe('42');
      expect(wrapper.find(wrappedElement('module-spring-cucumber-OPTIONAL-properties-stats')).text()).toBe('2 / 2');

      expect(wrapper.find(wrappedElement('module-spring-cucumber-MANDATORY-properties')).classes()).toEqual([
        'jhipster-module-properties',
        'selected',
        'all-valid-properties',
      ]);
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
  });

  describe('Module application', () => {
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

      const [message] = alertBus.success.lastCall.args;
      expect(message).toBe('Module "spring-cucumber" applied');
    });

    it('Should send module application error notification', async () => {
      const modules = repositoryWithModules();
      modules.apply.rejects(undefined);
      const wrapper = await filledModuleForm(modules);

      wrapper.find(wrappedElement('module-spring-cucumber-application-button')).trigger('click');
      await flushForm(wrapper);

      await flushPromises();

      const [message] = alertBus.error.lastCall.args;
      expect(message).toBe('Module "spring-cucumber" not applied');
    });
  });

  describe('Applied modules', () => {
    it('Should mark already applied modules as applied', async () => {
      const modules = repositoryWithModules();
      modules.history.resolves(moduleHistory());
      const wrapper = await filledModuleForm(modules);

      wrapper.find(wrappedElement('folder-path-field')).trigger('blur');

      await flushPromises();

      expect(wrapper.find(wrappedElement('module-spring-cucumber-application-icon')).classes()).toEqual(['bi', 'bi-arrow-clockwise']);
      expect(wrapper.find(wrappedElement('spring-cucumber-module-content')).classes()).toEqual([
        'jhipster-module--content',
        'selected',
        'applied',
      ]);
    });

    it('Should reset modules application for module history error', async () => {
      const modules = repositoryWithModules();
      modules.history.rejects();
      modules.apply.resolves(undefined);

      const wrapper = await filledModuleForm(modules);
      wrapper.find(wrappedElement('module-spring-cucumber-application-button')).trigger('click');
      await flushForm(wrapper);
      await flushPromises();

      wrapper.find(wrappedElement('folder-path-field')).trigger('blur');
      await flushPromises();

      expect(wrapper.find(wrappedElement('module-spring-cucumber-application-icon')).classes()).toEqual(['bi', 'bi-caret-right']);
    });

    it('Should mark modules as applied after application', async () => {
      const modules = repositoryWithModules();
      modules.apply.resolves(undefined);
      const wrapper = await filledModuleForm(modules);

      wrapper.find(wrappedElement('module-spring-cucumber-application-button')).trigger('click');
      await flushForm(wrapper);

      await flushPromises();

      expect(wrapper.find(wrappedElement('module-spring-cucumber-application-icon')).classes()).toEqual(['bi', 'bi-arrow-clockwise']);
    });
  });

  describe('Properties preload', () => {
    it('Should load properties from project', async () => {
      const modules = repositoryWithModules();
      modules.history.resolves(moduleHistory());
      const wrapper = wrap({ modules });

      await updatePath(wrapper);

      const baseNameField = wrapper.find(wrappedElement('property-baseName-field')).element as HTMLInputElement;
      expect(baseNameField.value).toBe('settedbase');
    });

    it('It should not override setted properties', async () => {
      const modules = repositoryWithModules();
      modules.history.resolves(moduleHistory());
      const wrapper = await filledModuleForm(modules);

      wrapper.find(wrappedElement('folder-path-field')).trigger('blur');
      await flushPromises();

      const baseNameField = wrapper.find(wrappedElement('property-baseName-field')).element as HTMLInputElement;
      expect(baseNameField.value).toBe('test');
    });
  });

  describe('Filtering', () => {
    it('Should filter modules with one matching slug module', async () => {
      const modules = repositoryWithModules();
      const wrapper = await filledModuleForm(modules);

      wrapper.find(wrappedElement('modules-filter-field')).setValue('spring-cucumber');
      await flushForm(wrapper);

      expect(wrapper.find(wrappedElement('module-banner-application-button')).exists()).toBe(false);
      expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).exists()).toBe(true);

      const modulesCountText = wrapper.find(wrappedElement('displayed-modules-count')).text();
      expect(modulesCountText).toContain('1');
      expect(modulesCountText).toContain('2');
    });

    it('Should filter modules with one matching description module', async () => {
      const modules = repositoryWithModules();
      const wrapper = await filledModuleForm(modules);

      wrapper.find(wrappedElement('modules-filter-field')).setValue('Add cucumber');
      await flushForm(wrapper);

      expect(wrapper.find(wrappedElement('module-banner-application-button')).exists()).toBe(false);
      expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).exists()).toBe(true);
    });

    it('Should filter modules with one matching tag module', async () => {
      const modules = repositoryWithModules();
      const wrapper = await filledModuleForm(modules);

      wrapper.find(wrappedElement('modules-filter-field')).setValue('server');
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

    it('Should filter modules with tag filter', async () => {
      const modules = repositoryWithModules();
      const wrapper = await filledModuleForm(modules);

      wrapper.find(wrappedElement('server-tag-filter')).trigger('click');
      await flushForm(wrapper);

      expect(wrapper.find(wrappedElement('module-banner-application-button')).exists()).toBe(false);
      expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).exists()).toBe(true);
      expect(wrapper.find(wrappedElement('server-tag-filter')).classes()).toEqual(['jhipster-modules-tags-filter', 'selected']);
    });

    it('Should filter modules with tag and search filter', async () => {
      const modules = repositoryWithModules();
      const wrapper = await filledModuleForm(modules);

      wrapper.find(wrappedElement('server-tag-filter')).trigger('click');
      wrapper.find(wrappedElement('modules-filter-field')).setValue('cucumber');
      await flushForm(wrapper);

      expect(wrapper.find(wrappedElement('module-banner-application-button')).exists()).toBe(false);
      expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).exists()).toBe(true);
    });

    it('Should deselect modules tag filter', async () => {
      const modules = repositoryWithModules();
      const wrapper = await filledModuleForm(modules);

      wrapper.find(wrappedElement('server-tag-filter')).trigger('click');
      await flushForm(wrapper);
      wrapper.find(wrappedElement('server-tag-filter')).trigger('click');
      await flushForm(wrapper);

      expect(wrapper.find(wrappedElement('module-banner-application-button')).exists()).toBe(true);
      expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).exists()).toBe(true);
      expect(wrapper.find(wrappedElement('server-tag-filter')).classes()).toEqual(['jhipster-modules-tags-filter', 'not-selected']);
    });
  });

  describe('Download', () => {
    it('Should disable download without project path', async () => {
      const wrapper = await componentWithModules();

      wrapper.find(wrappedElement('folder-path-field')).setValue('');
      await flushForm(wrapper);

      expect(wrapper.find(wrappedElement('download-button')).attributes('disabled')).toBeDefined();
    });

    it('Should disable applications during download', async () => {
      const modules = repositoryWithModules();
      modules.download.returns(new Promise(resolve => setTimeout(resolve, 500)));
      const wrapper = await filledModuleForm(modules);

      wrapper.find(wrappedElement('download-button')).trigger('click');
      await flushForm(wrapper);

      expect(wrapper.find(wrappedElement('download-button')).attributes('disabled')).toBeDefined();
      expect(wrapper.find(wrappedElement('module-spring-cucumber-application-button')).attributes('disabled')).toBeDefined();
    });

    it('Should download file using repository', async () => {
      const link = stubLink();
      windowStub.document.createElement.returns(link);

      const modules = repositoryWithModules();
      modules.download.resolves(defaultProject());
      const wrapper = await filledModuleForm(modules);

      wrapper.find(wrappedElement('download-button')).trigger('click');
      await flushForm(wrapper);
      await flushPromises();

      expect(wrapper.find(wrappedElement('download-button')).attributes('disabled')).toBeUndefined();
      expect(windowStub.URL.createObjectURL.calledOnce).toBeTruthy();
      expect(windowStub.document.createElement.calledOnce).toBeTruthy();
      expect(windowStub.document.body.appendChild.calledOnce).toBeTruthy();
      expect(windowStub.URL.revokeObjectURL.calledOnce).toBeTruthy();
      expect(windowStub.document.body.removeChild.calledOnce).toBeTruthy();

      expect(link.download).toBe('jhipster.zip');
      expect(modules.download.callCount).toBe(1);
    });

    it('Should handle download errors', async () => {
      const modules = repositoryWithModules();
      modules.download.rejects();
      const wrapper = await filledModuleForm(modules);

      wrapper.find(wrappedElement('download-button')).trigger('click');
      await flushForm(wrapper);

      await flushPromises();

      expect(wrapper.find(wrappedElement('download-button')).attributes('disabled')).toBeUndefined();
      expect(modules.download.callCount).toBe(1);

      const [message] = alertBus.error.lastCall.args;
      expect(message).toBe("Project can't be downloaded");
    });
  });
});

const componentWithModules = async (): Promise<VueWrapper> => {
  const modules = repositoryWithModules();
  const projectFolders = repositoryWithProjectFolders();
  const wrapper = wrap({ modules, projectFolders });

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

const repositoryWithProjectFolders = (): ProjectFoldersRepositoryStub => {
  const projectFolders = stubProjectFoldersRepository();
  projectFolders.get.resolves('/tmp/jhlite/1234');

  return projectFolders;
};

const updatePath = async (wrapper: VueWrapper): Promise<void> => {
  await flushPromises();
  await selectModule(wrapper);

  wrapper.find(wrappedElement('folder-path-field')).setValue('test');
  wrapper.find(wrappedElement('folder-path-field')).trigger('blur');
  await flushForm(wrapper);
  await flushPromises();
};
