import { ProjectActionsVue } from '@/module/primary/project-actions';
import { flushPromises, shallowMount, VueWrapper } from '@vue/test-utils';
import { stubAlertBus } from '../../../common/domain/AlertBus.fixture';
import { defaultProject, stubModulesRepository } from '../../domain/Modules.fixture';
import { ModulesRepository } from '@/module/domain/ModulesRepository';
import { wrappedElement } from '../../../WrappedElement';
import { stubWindow } from '../GlobalWindow.fixture';
import sinon from 'sinon';

interface WrapperOptions {
  folderPath: string;
  modules: ModulesRepository;
}

const alertBus = stubAlertBus();
const windowStub = stubWindow();

const wrap = (options?: Partial<WrapperOptions>): VueWrapper => {
  const { modules, folderPath }: WrapperOptions = {
    folderPath: '/dummy',
    modules: stubModulesRepository(),
    ...options,
  };

  return shallowMount(ProjectActionsVue, {
    props: {
      folderPath,
    },
    global: { provide: { modules, alertBus, globalWindow: windowStub } },
  });
};

const stubLink = () => ({
  href: '',
  click: sinon.stub(),
  download: '',
});

describe('Project actions', () => {
  describe('Formatting', () => {
    it('Should disable formatting without project path', async () => {
      const wrapper = wrap({ folderPath: '' });

      await flushForm(wrapper);

      expect(wrapper.find(wrappedElement('format-button')).attributes('disabled')).toBeDefined();
    });
  });

  it('Should format file using repository', async () => {
    const modules = stubModulesRepository();
    modules.format.resolves(defaultProject());
    const wrapper = wrap({ modules });

    wrapper.find(wrappedElement('format-button')).trigger('click');
    await flushForm(wrapper);
    await flushPromises();

    expect(wrapper.find(wrappedElement('format-button')).attributes('disabled')).toBeUndefined();
    expect(modules.format.callCount).toBe(1);

    const [message] = alertBus.success.lastCall.args;
    expect(message).toBe('Project formatted');
  });

  it('Should handle formatting errors', async () => {
    const modules = stubModulesRepository();
    modules.format.rejects();
    const wrapper = wrap({ modules });

    wrapper.find(wrappedElement('format-button')).trigger('click');
    await flushForm(wrapper);
    await flushPromises();

    expect(wrapper.find(wrappedElement('format-button')).attributes('disabled')).toBeUndefined();
    expect(modules.format.callCount).toBe(1);

    const [message] = alertBus.error.lastCall.args;
    expect(message).toBe("Project can't be formatted");
  });

  describe('Download', () => {
    it('Should disable download without project path', async () => {
      const wrapper = wrap({ folderPath: '' });

      await flushForm(wrapper);

      expect(wrapper.find(wrappedElement('download-button')).attributes('disabled')).toBeDefined();
    });

    it('Should download file using repository', async () => {
      const link = stubLink();
      windowStub.document.createElement.returns(link);

      const modules = stubModulesRepository();
      modules.download.resolves(defaultProject());
      const wrapper = wrap({ modules });

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
      const modules = stubModulesRepository();
      modules.download.rejects();
      const wrapper = wrap({ modules });

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

const flushForm = async (wrapper: VueWrapper) => {
  await wrapper.vm.$nextTick();
};
