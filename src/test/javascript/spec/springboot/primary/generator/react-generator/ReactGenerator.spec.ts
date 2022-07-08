import { shallowMount, VueWrapper } from '@vue/test-utils';
import { ProjectToUpdate } from '@/springboot/primary/ProjectToUpdate';
import { createProjectToUpdate } from '../../ProjectToUpdate.fixture';
import { ReactService } from '@/springboot/domain/client/ReactService';
import { stubReactService } from '../../../domain/client/ReactService.fixture';
import { ReactGeneratorVue } from '@/springboot/primary/generator/react-generator';
import { AlertBus } from '@/common/domain/alert/AlertBus';
import { AlertBusFixture, stubAlertBus } from '../../../../common/domain/AlertBus.fixture';
import { projectJson } from '../RestProject.fixture';
import { ProjectService } from '../../../../../../../main/webapp/app/springboot/domain/ProjectService';
import { stubProjectService } from '../../../domain/ProjectService.fixture';

let wrapper: VueWrapper;
let component: any;

interface WrapperOptions {
  alertBus: AlertBus;
  reactService: ReactService;
  projectService: ProjectService;
  project: ProjectToUpdate;
}
const expectAlertErrorToBe = (alertBus: AlertBusFixture, message: string) => {
  const [alertMessage] = alertBus.error.getCall(0).args;
  expect(alertMessage).toBe(message);
};

const expectAlertSuccessToBe = (alertBus: AlertBusFixture, message: string) => {
  const [alertMessage] = alertBus.success.getCall(0).args;
  expect(alertMessage).toBe(message);
};
const wrap = (wrapperOptions?: Partial<WrapperOptions>) => {
  const { alertBus, reactService, projectService, project }: WrapperOptions = {
    alertBus: stubAlertBus(),
    reactService: stubReactService(),
    projectService: stubProjectService(),
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
        projectService,
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
    const alertBus = stubAlertBus();
    await wrap({ reactService, project: createProjectToUpdate({ folder: 'project/path' }), alertBus });

    await component.addReact();

    const args = reactService.add.getCall(0).args[0];
    expect(args).toEqual(projectJson);
    expectAlertSuccessToBe(alertBus, 'React successfully added');
  });

  it('should add React with Style when checkbox is checked', async () => {
    const reactService = stubReactService();
    const alertBus = stubAlertBus();
    reactService.addWithStyle.resolves({});
    await wrap({ reactService, project: createProjectToUpdate({ folder: 'project/path' }), alertBus });

    const checkbox = wrapper.find('#react-with-style');
    await checkbox.setValue(true);
    await component.addReact();

    const args = reactService.addWithStyle.getCall(0).args[0];
    expect(args).toEqual(projectJson);
    expectAlertSuccessToBe(alertBus, 'React with style successfully added');
  });

  it('should handle error on adding React failure', async () => {
    const alertBus = stubAlertBus();
    const reactService = stubReactService();
    reactService.add.rejects('error');
    await wrap({ alertBus, reactService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addReact();
    expectAlertErrorToBe(alertBus, 'Adding React to project failed error');
  });

  it('should handle error on adding React with style failure', async () => {
    const alertBus = stubAlertBus();
    const reactService = stubReactService();
    reactService.addWithStyle.rejects('error');
    await wrap({ alertBus, reactService, project: createProjectToUpdate({ folder: 'project/path' }) });

    const checkbox = wrapper.find('#react-with-style');
    await checkbox.setValue(true);
    await component.addReact();
    expectAlertErrorToBe(alertBus, 'Adding React with style to project failed error');
  });

  it('should not add Cypress when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addCypress.resolves({});
    await wrap({ projectService, project: createProjectToUpdate({ folder: '' }) });

    await component.addCypressForReact();

    expect(projectService.addCypress.called).toBe(false);
  });

  it('should add Cypress when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addCypress.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ projectService, project: createProjectToUpdate({ folder: 'project/path' }), alertBus });

    await component.addCypressForReact();

    const args = projectService.addCypress.getCall(0).args[0];
    expect(args).toEqual(projectJson);
    expectAlertSuccessToBe(alertBus, 'Cypress successfully added');
  });

  it('should handle error on adding Cypress failure', async () => {
    const projectService = stubProjectService();
    const alertBus = stubAlertBus();
    projectService.addCypress.rejects('error');
    await wrap({ projectService, project: createProjectToUpdate({ folder: 'path' }), alertBus });

    await component.addCypressForReact();

    expectAlertErrorToBe(alertBus, 'Adding Cypress to project failed error');
  });

  it('should not add React with JWT when project path is not filled', async () => {
    const reactService = stubReactService();
    reactService.addWithJWT.resolves({});
    await wrap({ reactService, project: createProjectToUpdate({ folder: '' }) });

    await component.addReactWithJWT();

    expect(reactService.addWithJWT.called).toBe(false);
  });

  it('should add React with JWT when project path is filled', async () => {
    const reactService = stubReactService();
    reactService.addWithJWT.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ reactService, project: createProjectToUpdate({ folder: 'project/path' }), alertBus });

    await component.addReactWithJWT();

    const args = reactService.addWithJWT.getCall(0).args[0];
    expect(args).toEqual(projectJson);
    expectAlertSuccessToBe(alertBus, 'React with authentication JWT successfully added');
  });

  it('should handle error on adding React with JWT failure', async () => {
    const reactService = stubReactService();
    const alertBus = stubAlertBus();
    reactService.addWithJWT.rejects('error');
    await wrap({ reactService, project: createProjectToUpdate({ folder: 'path' }), alertBus });

    await component.addReactWithJWT();

    expectAlertErrorToBe(alertBus, 'Adding React with authentication JWT to project failed error');
  });

  it('should not add Playwright when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addPlaywright.resolves({});
    await wrap({ projectService, project: createProjectToUpdate({ folder: '' }) });

    await component.addPlaywrightForReact();

    expect(projectService.addPlaywright.called).toBe(false);
  });

  it('should add Playwright when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addPlaywright.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ projectService, project: createProjectToUpdate({ folder: 'project/path' }), alertBus });

    await component.addPlaywrightForReact();

    const args = projectService.addPlaywright.getCall(0).args[0];
    expect(args).toEqual(projectJson);
    expectAlertSuccessToBe(alertBus, 'Playwright successfully added');
  });

  it('should handle error on adding Playwright failure', async () => {
    const projectService = stubProjectService();
    const alertBus = stubAlertBus();
    projectService.addPlaywright.rejects('error');
    await wrap({ projectService, project: createProjectToUpdate({ folder: 'path' }), alertBus });

    await component.addPlaywrightForReact();

    expectAlertErrorToBe(alertBus, 'Adding Playwright to project failed error');
  });
});
