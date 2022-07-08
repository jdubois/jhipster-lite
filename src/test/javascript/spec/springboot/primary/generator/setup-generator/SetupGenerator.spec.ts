import { shallowMount, VueWrapper } from '@vue/test-utils';
import { AlertBus } from '@/common/domain/alert/AlertBus';
import { ProjectToUpdate } from '@/springboot/primary/ProjectToUpdate';
import { AlertBusFixture, stubAlertBus } from '../../../../common/domain/AlertBus.fixture';
import { createProjectToUpdate } from '../../ProjectToUpdate.fixture';
import { stubSetupService } from '../../../domain/SetupService.fixture';
import { SetupService } from '@/springboot/domain/SetupService';
import { SetupGeneratorVue } from '@/springboot/primary/generator/setup-generator';
import { stubSpringBootService } from '../../../domain/SpringBootService.fixture';
import { projectJson } from '../RestProject.fixture';
import { set } from 'husky';

let wrapper: VueWrapper;
let component: any;

interface WrapperOptions {
  alertBus: AlertBus;
  setupService: SetupService;
  project: ProjectToUpdate;
}
const wrap = (wrapperOptions?: Partial<WrapperOptions>) => {
  const { alertBus, setupService, project }: WrapperOptions = {
    alertBus: stubAlertBus(),
    setupService: stubSetupService(),
    project: createProjectToUpdate(),
    ...wrapperOptions,
  };
  wrapper = shallowMount(SetupGeneratorVue, {
    props: {
      project,
    },
    global: {
      provide: {
        alertBus,
        setupService,
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
describe('SetupGenerator', () => {
  it('should exist', () => {
    wrap();

    expect(wrapper.exists()).toBe(true);
  });

  it('should not add Github Actions when project path is not filled', async () => {
    const setupService = stubSetupService();
    setupService.addGithubActions.resolves({});
    await wrap({ setupService, project: createProjectToUpdate({ folder: '' }) });

    await component.addGithubActions();

    expect(setupService.addGithubActions.called).toBe(false);
  });

  it('should add Github Actions when project path is filled', async () => {
    const setupService = stubSetupService();
    setupService.addGithubActions.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ alertBus, setupService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addGithubActions();

    const args = setupService.addGithubActions.getCall(0).args[0];
    expect(args).toEqual(projectJson);
    expectAlertSuccessToBe(alertBus, 'Github actions successfully added');
  });

  it('should handle error on adding github actions failure', async () => {
    const setupService = stubSetupService();
    setupService.addGithubActions.rejects('error');
    const alertBus = stubAlertBus();
    await wrap({ alertBus, setupService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addGithubActions();

    expectAlertErrorToBe(alertBus, 'Adding Github actions to project failed error');
  });
});
