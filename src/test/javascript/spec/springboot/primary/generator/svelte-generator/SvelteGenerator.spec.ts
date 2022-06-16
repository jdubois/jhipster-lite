import { shallowMount, VueWrapper } from '@vue/test-utils';
import { ProjectToUpdate } from '@/springboot/primary/ProjectToUpdate';
import { createProjectToUpdate } from '../../ProjectToUpdate.fixture';
import { SvelteGeneratorVue } from '@/springboot/primary/generator/svelte-generator';
import { stubSvelteService } from '../../../domain/client/SvelteService.fixture';
import { SvelteService } from '../../../../../../../main/webapp/app/springboot/domain/client/SvelteService';
import { AlertBusFixture, stubAlertBus } from '../../../../common/domain/AlertBus.fixture';
import { projectJson } from '../RestProject.fixture';

let wrapper: VueWrapper;
let component: any;

interface WrapperOptions {
  alertBus: AlertBusFixture;
  project: ProjectToUpdate;
  svelteService: SvelteService;
}

const wrap = (wrapperOptions?: Partial<WrapperOptions>) => {
  const { alertBus, project, svelteService }: WrapperOptions = {
    alertBus: stubAlertBus(),
    project: createProjectToUpdate(),
    svelteService: stubSvelteService(),
    ...wrapperOptions,
  };
  wrapper = shallowMount(SvelteGeneratorVue, {
    props: {
      project,
    },
    global: {
      provide: {
        alertBus,
        svelteService,
      },
    },
  });
  component = wrapper.vm;
};

describe('SvelteGenerator', () => {
  it('should exist', () => {
    wrap();

    expect(wrapper.exists()).toBe(true);
  });

  it('should add Svelte', async () => {
    const svelteService = stubSvelteService();
    await wrap({ svelteService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSvelte();

    const [addedProject] = svelteService.add.getCall(0).args;
    expect(addedProject).toEqual(projectJson);
  });

  it('should not add Svelte when project path is not filled', async () => {
    const svelteService = stubSvelteService();
    await wrap({ svelteService, project: createProjectToUpdate({ folder: '' }) });

    await component.addSvelte();

    expect(svelteService.add.called).toBe(false);
  });

  it('should add Svelte with style when checkbox is checked', async () => {
    const svelteService = stubSvelteService();
    await wrap({ svelteService });

    const checkbox = wrapper.find('#svelte-with-style');
    await checkbox.setValue(true);
    await component.addSvelte();

    expect(svelteService.addWithStyle.called).toBeTruthy();
  });

  it('should handle error on adding Svelte failure', async () => {
    const alertBus = stubAlertBus();
    const svelteService = stubSvelteService();
    svelteService.add.rejects('error');
    await wrap({ alertBus, svelteService });

    await component.addSvelte();

    const [message] = alertBus.error.getCall(0).args;
    expect(message).toBe('Adding Svelte to project failed error');
  });

  it('should handle success on adding Svelte', async () => {
    const alertBus = stubAlertBus();
    const svelteService = stubSvelteService();
    await wrap({ alertBus, svelteService });

    await component.addSvelte();

    const [message] = alertBus.success.getCall(0).args;
    expect(message).toBe('Svelte successfully added');
  });
});
