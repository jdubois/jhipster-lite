import { shallowMount, VueWrapper } from '@vue/test-utils';
import { ProjectToUpdate } from '@/springboot/primary/ProjectToUpdate';
import { createProjectToUpdate } from '../../ProjectToUpdate.fixture';
import { VueService } from '@/springboot/domain/client/VueService';
import { stubVueService } from '../../../domain/client/VueService.fixture';
import { VueGeneratorVue } from '@/springboot/primary/generator/vue-generator';
import { AlertBus } from '@/common/domain/alert/AlertBus';
import { AlertBusFixture, stubAlertBus } from '../../../../common/domain/AlertBus.fixture';
import { projectJson } from '../RestProject.fixture';
import { ProjectService } from '../../../../../../../main/webapp/app/springboot/domain/ProjectService';
import { stubProjectService } from '../../../domain/ProjectService.fixture';

let wrapper: VueWrapper;
let component: any;

interface WrapperOptions {
  alertBus: AlertBus;
  vueService: VueService;
  projectService: ProjectService;
  project: ProjectToUpdate;
}

const wrap = (wrapperOptions?: Partial<WrapperOptions>) => {
  const { alertBus, vueService, projectService, project }: WrapperOptions = {
    alertBus: stubAlertBus(),
    vueService: stubVueService(),
    projectService: stubProjectService(),
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
        projectService,
      },
    },
  });
  component = wrapper.vm;
};
const expectAlertErrorToBe = (alertBus: AlertBusFixture, message: string) => {
  const [alertMessage] = alertBus.error.getCall(0).args;
  expect(alertMessage).toBe(message);
};

const expectAlertSuccessToBe = (alertBus: AlertBusFixture, message: string) => {
  const [alertMessage] = alertBus.success.getCall(0).args;
  expect(alertMessage).toBe(message);
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
    const alertBus = stubAlertBus();
    await wrap({ vueService, project: createProjectToUpdate({ folder: 'project/path' }), alertBus });

    await component.addVue();

    const args = vueService.add.getCall(0).args[0];
    expect(args).toEqual(projectJson);
    expectAlertSuccessToBe(alertBus, 'Vue successfully added');
  });

  it('should handle error on adding Vue failure', async () => {
    const alertBus = stubAlertBus();
    const vueService = stubVueService();
    vueService.add.rejects('error');
    await wrap({ alertBus, vueService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addVue();
    expectAlertErrorToBe(alertBus, 'Adding Vue to project failed error');
  });

  it('should not add Cypress when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addCypress.resolves({});
    await wrap({ projectService, project: createProjectToUpdate({ folder: '' }) });

    await component.addCypressForVue();

    expect(projectService.addCypress.called).toBe(false);
  });

  it('should add Cypress when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addCypress.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ projectService, project: createProjectToUpdate({ folder: 'project/path' }), alertBus });

    await component.addCypressForVue();

    const args = projectService.addCypress.getCall(0).args[0];
    expect(args).toEqual(projectJson);
    expectAlertSuccessToBe(alertBus, 'Cypress successfully added');
  });

  it('should handle error on adding Cypress failure', async () => {
    const projectService = stubProjectService();
    const alertBus = stubAlertBus();
    projectService.addCypress.rejects('error');
    await wrap({ projectService, project: createProjectToUpdate({ folder: 'path' }), alertBus });

    await component.addCypressForVue();

    expectAlertErrorToBe(alertBus, 'Adding Cypress to project failed error');
  });

  it('should not add Playwright when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addPlaywright.resolves({});
    await wrap({ projectService, project: createProjectToUpdate({ folder: '' }) });

    await component.addPlaywrightForVue();

    expect(projectService.addPlaywright.called).toBe(false);
  });

  it('should add Playwright when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addPlaywright.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ projectService, project: createProjectToUpdate({ folder: 'project/path' }), alertBus });

    await component.addPlaywrightForVue();

    const args = projectService.addPlaywright.getCall(0).args[0];
    expect(args).toEqual(projectJson);
    expectAlertSuccessToBe(alertBus, 'Playwright successfully added');
  });

  it('should handle error on adding Playwright failure', async () => {
    const projectService = stubProjectService();
    const alertBus = stubAlertBus();
    projectService.addPlaywright.rejects('error');
    await wrap({ projectService, project: createProjectToUpdate({ folder: 'path' }), alertBus });

    await component.addPlaywrightForVue();

    expectAlertErrorToBe(alertBus, 'Adding Playwright to project failed error');
  });
});
