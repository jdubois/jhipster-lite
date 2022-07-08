import { shallowMount, VueWrapper } from '@vue/test-utils';
import { stubAngularService } from '../../../domain/client/AngularService.fixture';
import { ProjectToUpdate } from '@/springboot/primary/ProjectToUpdate';
import { createProjectToUpdate } from '../../ProjectToUpdate.fixture';
import { AngularService } from '@/springboot/domain/client/AngularService';
import { AngularGeneratorVue } from '@/springboot/primary/generator/angular-generator';
import { AlertBusFixture, stubAlertBus } from '../../../../common/domain/AlertBus.fixture';
import { AlertBus } from '@/common/domain/alert/AlertBus';
import { projectJson } from '../RestProject.fixture';
import { stubProjectService } from '../../../domain/ProjectService.fixture';
import { ProjectService } from '../../../../../../../main/webapp/app/springboot/domain/ProjectService';

let wrapper: VueWrapper;
let component: InstanceType<typeof AngularGeneratorVue>;

interface WrapperOptions {
  alertBus: AlertBus;
  angularService: AngularService;
  projectService: ProjectService;
  project: ProjectToUpdate;
}

const wrap = (wrapperOptions?: Partial<WrapperOptions>) => {
  const { alertBus, angularService, projectService, project }: WrapperOptions = {
    alertBus: stubAlertBus(),
    angularService: stubAngularService(),
    projectService: stubProjectService(),
    project: createProjectToUpdate(),
    ...wrapperOptions,
  };
  wrapper = shallowMount(AngularGeneratorVue, {
    props: {
      project,
    },
    global: {
      provide: {
        alertBus,
        angularService,
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

describe('AngularGenerator', () => {
  it('should exist', () => {
    wrap();

    expect(wrapper.exists()).toBe(true);
  });

  it('should not add Angular when project path is not filled', async () => {
    const angularService = stubAngularService();
    angularService.add.resolves({});
    await wrap({ angularService, project: createProjectToUpdate({ folder: '' }) });

    await component.addAngular();

    expect(angularService.add.called).toBe(false);
  });

  it('should add Angular when project path is filled', async () => {
    const angularService = stubAngularService();
    angularService.add.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ angularService, project: createProjectToUpdate({ folder: 'project/path' }), alertBus });

    await component.addAngular();

    const args = angularService.add.getCall(0).args[0];
    expect(args).toEqual(projectJson);
    expectAlertSuccessToBe(alertBus, 'Angular successfully added');
  });

  it('should handle error on adding Angular failure', async () => {
    const angularService = stubAngularService();
    const alertBus = stubAlertBus();
    angularService.add.rejects('error');
    await wrap({ angularService, project: createProjectToUpdate({ folder: 'path' }), alertBus });

    await component.addAngular();

    expectAlertErrorToBe(alertBus, 'Adding Angular to project failed error');
  });

  it('should not add Angular with JWT when project path is not filled', async () => {
    const angularService = stubAngularService();
    angularService.addWithJWT.resolves({});
    await wrap({ angularService, project: createProjectToUpdate({ folder: '' }) });

    await component.addAngularWithJWT();

    expect(angularService.addWithJWT.called).toBe(false);
  });

  it('should add Angular with JWT when project path is filled', async () => {
    const angularService = stubAngularService();
    angularService.addWithJWT.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ angularService, project: createProjectToUpdate({ folder: 'project/path' }), alertBus });

    await component.addAngularWithJWT();

    const args = angularService.addWithJWT.getCall(0).args[0];
    expect(args).toEqual(projectJson);
    expectAlertSuccessToBe(alertBus, 'Angular with authentication JWT successfully added');
  });

  it('should handle error on adding Angular with JWT failure', async () => {
    const angularService = stubAngularService();
    const alertBus = stubAlertBus();
    angularService.addWithJWT.rejects('error');
    await wrap({ angularService, project: createProjectToUpdate({ folder: 'path' }), alertBus });

    await component.addAngularWithJWT();

    expectAlertErrorToBe(alertBus, 'Adding Angular with authentication JWT to project failed error');
  });

  it('should not add Oauth2 when project path is not filled', async () => {
    const angularService = stubAngularService();
    angularService.addOauth2.resolves({});
    await wrap({ angularService, project: createProjectToUpdate({ folder: '' }) });

    await component.addOauth2();

    expect(angularService.addOauth2.called).toBe(false);
  });

  it('should add Oauth2 when project path is filled', async () => {
    const angularService = stubAngularService();
    angularService.addOauth2.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ angularService, project: createProjectToUpdate({ folder: 'project/path' }), alertBus });

    await component.addOauth2();

    const args = angularService.addOauth2.getCall(0).args[0];
    expect(args).toEqual(projectJson);
    expectAlertSuccessToBe(alertBus, 'OAuth2 successfully added');
  });

  it('should handle error on adding Oauth2 failure', async () => {
    const angularService = stubAngularService();
    const alertBus = stubAlertBus();
    angularService.addOauth2.rejects('error');
    await wrap({ angularService, project: createProjectToUpdate({ folder: 'path' }), alertBus });

    await component.addOauth2();

    expectAlertErrorToBe(alertBus, 'Adding Oauth2 to project failed error');
  });

  it('should not add Health when project path is not filled', async () => {
    const angularService = stubAngularService();
    angularService.addHealth.resolves({});
    await wrap({ angularService, project: createProjectToUpdate({ folder: '' }) });

    await component.addHealth();

    expect(angularService.addHealth.called).toBe(false);
  });

  it('should add Health when project path is filled', async () => {
    const angularService = stubAngularService();
    angularService.addHealth.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ angularService, project: createProjectToUpdate({ folder: 'project/path' }), alertBus });

    await component.addHealth();

    const args = angularService.addHealth.getCall(0).args[0];
    expect(args).toEqual(projectJson);
    expectAlertSuccessToBe(alertBus, 'Health successfully added');
  });

  it('should handle error on adding Health failure', async () => {
    const angularService = stubAngularService();
    const alertBus = stubAlertBus();
    angularService.addHealth.rejects('error');
    await wrap({ angularService, project: createProjectToUpdate({ folder: 'path' }), alertBus });

    await component.addHealth();

    expectAlertErrorToBe(alertBus, 'Adding Health to project failed error');
  });

  it('should not add Cypress when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addCypress.resolves({});
    await wrap({ projectService, project: createProjectToUpdate({ folder: '' }) });

    await component.addCypressForAngular();

    expect(projectService.addCypress.called).toBe(false);
  });

  it('should add Cypress when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addCypress.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ projectService, project: createProjectToUpdate({ folder: 'project/path' }), alertBus });

    await component.addCypressForAngular();

    const args = projectService.addCypress.getCall(0).args[0];
    expect(args).toEqual(projectJson);
    expectAlertSuccessToBe(alertBus, 'Cypress successfully added');
  });

  it('should handle error on adding Cypress failure', async () => {
    const projectService = stubProjectService();
    const alertBus = stubAlertBus();
    projectService.addCypress.rejects('error');
    await wrap({ projectService, project: createProjectToUpdate({ folder: 'path' }), alertBus });

    await component.addCypressForAngular();

    expectAlertErrorToBe(alertBus, 'Adding Cypress to project failed error');
  });

  it('should not add Playwright when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addPlaywright.resolves({});
    await wrap({ projectService, project: createProjectToUpdate({ folder: '' }) });

    await component.addPlaywrightForAngular();

    expect(projectService.addPlaywright.called).toBe(false);
  });

  it('should add Playwright when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addPlaywright.resolves({});
    const alertBus = stubAlertBus();
    await wrap({ projectService, project: createProjectToUpdate({ folder: 'project/path' }), alertBus });

    await component.addPlaywrightForAngular();

    const args = projectService.addPlaywright.getCall(0).args[0];
    expect(args).toEqual(projectJson);
    expectAlertSuccessToBe(alertBus, 'Playwright successfully added');
  });

  it('should handle error on adding Playwright failure', async () => {
    const projectService = stubProjectService();
    const alertBus = stubAlertBus();
    projectService.addPlaywright.rejects('error');
    await wrap({ projectService, project: createProjectToUpdate({ folder: 'path' }), alertBus });

    await component.addPlaywrightForAngular();

    expectAlertErrorToBe(alertBus, 'Adding Playwright to project failed error');
  });
});
