import { shallowMount, VueWrapper } from '@vue/test-utils';
import { ProjectToUpdate } from '@/springboot/primary/ProjectToUpdate';
import { createProjectToUpdate } from '../../ProjectToUpdate.fixture';
import { VueService } from '@/springboot/domain/client/VueService';
import { stubVueService } from '../../../domain/client/VueService.fixture';
import { VueGeneratorVue } from '@/springboot/primary/generator/vue-generator';
import { AlertBus } from '@/common/domain/alert/AlertBus';
import { stubAlertBus } from '../../../../common/domain/AlertBus.fixture';
import { projectJson } from '../RestProject.fixture';

let wrapper: VueWrapper;
let component: any;

interface WrapperOptions {
  alertBus: AlertBus;
  vueService: VueService;
  project: ProjectToUpdate;
}

const wrap = (wrapperOptions?: Partial<WrapperOptions>) => {
  const { alertBus, vueService, project }: WrapperOptions = {
    alertBus: stubAlertBus(),
    vueService: stubVueService(),
    project: createProjectToUpdate(),
    ...wrapperOptions,
  };
  wrapper = shallowMount(VueGeneratorVue, {
    props: {
      project,
    },
    global: {
      provide: {
        alertBus,
        vueService,
      },
    },
  });
  component = wrapper.vm;
};

describe('VueGenerator', () => {
  it('should exist', () => {
    wrap();

    expect(wrapper.exists()).toBe(true);
  });

  it('should not add Vue when project path is not filled', async () => {
    const vueService = stubVueService();
    vueService.add.resolves({});
    await wrap({ vueService, project: createProjectToUpdate({ folder: '' }) });

    await component.addVue();

    expect(vueService.add.called).toBe(false);
  });

  it('should add Vue when project path is filled', async () => {
    const vueService = stubVueService();
    vueService.add.resolves({});
    await wrap({ vueService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addVue();

    const args = vueService.add.getCall(0).args[0];
    expect(args).toEqual(projectJson);
  });

  it('should handle error on adding Vue failure', async () => {
    const alertBus = stubAlertBus();
    const vueService = stubVueService();
    vueService.add.rejects('error');
    await wrap({ alertBus, vueService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addVue();

    const [message] = alertBus.error.getCall(0).args;
    expect(message).toBe('Adding Vue to project failed error');
  });
});
