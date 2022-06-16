import { shallowMount, VueWrapper } from '@vue/test-utils';
import { ProjectToUpdate } from '@/springboot/primary/ProjectToUpdate';
import { createProjectToUpdate } from '../../ProjectToUpdate.fixture';
import { ReactService } from '@/springboot/domain/client/ReactService';
import { stubReactService } from '../../../domain/client/ReactService.fixture';
import { ReactGeneratorVue } from '@/springboot/primary/generator/react-generator';
import { AlertBus } from '@/common/domain/alert/AlertBus';
import { stubAlertBus } from '../../../../common/domain/AlertBus.fixture';
import { projectJson } from '../RestProject.fixture';

let wrapper: VueWrapper;
let component: any;

interface WrapperOptions {
  alertBus: AlertBus;
  reactService: ReactService;
  project: ProjectToUpdate;
}

const wrap = (wrapperOptions?: Partial<WrapperOptions>) => {
  const { alertBus, reactService, project }: WrapperOptions = {
    alertBus: stubAlertBus(),
    reactService: stubReactService(),
    project: createProjectToUpdate(),
    ...wrapperOptions,
  };
  wrapper = shallowMount(ReactGeneratorVue, {
    props: {
      project,
    },
    global: {
      provide: {
        alertBus,
        reactService,
      },
    },
  });
  component = wrapper.vm;
};

describe('ReactGenerator', () => {
  it('should exist', () => {
    wrap();

    expect(wrapper.exists()).toBe(true);
  });

  it('should not add React when project path is not filled', async () => {
    const reactService = stubReactService();
    reactService.add.resolves({});
    await wrap({ reactService, project: createProjectToUpdate({ folder: '' }) });

    await component.addReact();

    expect(reactService.add.called).toBe(false);
  });

  it('should add React when project path is filled', async () => {
    const reactService = stubReactService();
    reactService.add.resolves({});
    await wrap({ reactService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addReact();

    const args = reactService.add.getCall(0).args[0];
    expect(args).toEqual(projectJson);
  });

  it('should add React with Style when checkbox is checked', async () => {
    const reactService = stubReactService();
    reactService.addWithStyle.resolves({});
    await wrap({ reactService, project: createProjectToUpdate({ folder: 'project/path' }) });

    const checkbox = wrapper.find('#react-with-style');
    await checkbox.setValue(true);
    await component.addReact();

    const args = reactService.addWithStyle.getCall(0).args[0];
    expect(args).toEqual(projectJson);
  });

  it('should handle error on adding React failure', async () => {
    const alertBus = stubAlertBus();
    const reactService = stubReactService();
    reactService.add.rejects('error');
    await wrap({ alertBus, reactService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addReact();

    const [message] = alertBus.error.getCall(0).args;
    expect(message).toBe('Adding React to project failed error');
  });

  it('should handle error on adding React with style failure', async () => {
    const alertBus = stubAlertBus();
    const reactService = stubReactService();
    reactService.addWithStyle.rejects('error');
    await wrap({ alertBus, reactService, project: createProjectToUpdate({ folder: 'project/path' }) });

    const checkbox = wrapper.find('#react-with-style');
    await checkbox.setValue(true);
    await component.addReact();

    const [message] = alertBus.error.getCall(0).args;
    expect(message).toBe('Adding React with style to project failed error');
  });
});
