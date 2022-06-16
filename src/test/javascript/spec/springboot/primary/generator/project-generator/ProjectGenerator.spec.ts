import { shallowMount, VueWrapper } from '@vue/test-utils';
import { ProjectToUpdate } from '@/springboot/primary/ProjectToUpdate';
import { createProjectToUpdate } from '../../ProjectToUpdate.fixture';
import { ProjectService } from '@/springboot/domain/ProjectService';
import { stubProjectService } from '../../../domain/ProjectService.fixture';
import { ProjectGeneratorVue } from '@/springboot/primary/generator/project-generator';
import { FileDownloader } from '@/common/primary/FileDownloader';
import { stubFileDownloader } from '../../../../common/primary/FileDownloader.fixture';
import { stubAlertBus } from '../../../../common/domain/AlertBus.fixture';
import { AlertBus } from '@/common/domain/alert/AlertBus';
import { projectJson } from '../RestProject.fixture';

let wrapper: VueWrapper;
let component: any;

interface WrapperOptions {
  alertBus: AlertBus;
  projectService: ProjectService;
  fileDownloader: FileDownloader;
  project: ProjectToUpdate;
}

const wrap = (wrapperOptions?: Partial<WrapperOptions>) => {
  const { alertBus, projectService, fileDownloader, project }: WrapperOptions = {
    alertBus: stubAlertBus(),
    projectService: stubProjectService(),
    fileDownloader: stubFileDownloader(),
    project: createProjectToUpdate(),
    ...wrapperOptions,
  };
  wrapper = shallowMount(ProjectGeneratorVue, {
    props: {
      project,
      buildTool: 'maven',
      setupTool: '',
    },
    global: {
      provide: {
        alertBus,
        projectService,
        fileDownloader,
      },
    },
  });
  component = wrapper.vm;
};

describe('ProjectGenerator', () => {
  it('should exist', () => {
    wrap();

    expect(wrapper.exists()).toBe(true);
  });

  it('should not init project when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.init.resolves({});
    await wrap({ projectService, project: createProjectToUpdate({ folder: '' }) });

    await component.initProject();

    expect(projectService.init.called).toBe(false);
  });

  it('should init project when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.init.resolves({});
    const alertBus = stubAlertBus();
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await wrap({ alertBus, projectService, project: projectToUpdate });

    await component.initProject();

    const args = projectService.init.getCall(0).args[0];
    expect(args).toEqual(projectJson);
    const alertMessage = alertBus.success.getCall(0).args[0];
    expect(alertMessage).toBe('Project successfully initialized');
  });

  it('should handle error on init failure', async () => {
    const alertBus = stubAlertBus();
    const projectService = stubProjectService();
    projectService.init.rejects('error');
    await wrap({ alertBus, projectService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.initProject();

    const [message] = alertBus.error.getCall(0).args;
    expect(message).toBe('Project initialization failed error');
  });

  it('should not add Maven when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addMaven.resolves({});
    await wrap({ projectService, project: createProjectToUpdate({ folder: '' }) });

    await component.addMaven();

    expect(projectService.addMaven.called).toBe(false);
  });

  it('should add Maven when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addMaven.resolves({});
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await wrap({ projectService, project: projectToUpdate });

    await component.addMaven();

    const args = projectService.addMaven.getCall(0).args[0];
    expect(args).toEqual(projectJson);
  });

  it('should handle error on adding maven failure', async () => {
    const alertBus = stubAlertBus();
    const projectService = stubProjectService();
    projectService.addMaven.rejects('error');
    await wrap({ alertBus, projectService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addMaven();

    const [message] = alertBus.error.getCall(0).args;
    expect(message).toBe('Adding Maven to project failed error');
  });

  it('should not add Codespaces setup when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addCodespacesSetup.resolves({});
    await wrap({ projectService, project: createProjectToUpdate({ folder: '' }) });

    await component.addCodespacesSetup();

    expect(projectService.addCodespacesSetup.called).toBe(false);
  });

  it('should add Codespaces setup when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addCodespacesSetup.resolves({});
    await wrap({ projectService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await wrap({ projectService, project: projectToUpdate });

    await component.addCodespacesSetup();

    const args = projectService.addCodespacesSetup.getCall(0).args[0];
    expect(args).toEqual(projectJson);
  });

  it('should handle error on adding Codespaces failure', async () => {
    const alertBus = stubAlertBus();
    const projectService = stubProjectService();
    projectService.addCodespacesSetup.rejects('error');
    await wrap({ alertBus, projectService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addCodespacesSetup();

    const [message] = alertBus.error.getCall(0).args;
    expect(message).toBe('Adding Codespaces setup to project failed error');
  });

  it('should not add Gitpod setup when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addGitpodSetup.resolves({});
    await wrap({ projectService, project: createProjectToUpdate({ folder: '' }) });

    await component.addGitpodSetup();

    expect(projectService.addGitpodSetup.called).toBe(false);
  });

  it('should add Gitpod setup when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addGitpodSetup.resolves({});
    await wrap({ projectService });
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await wrap({ projectService, project: projectToUpdate });

    await component.addGitpodSetup();

    const args = projectService.addGitpodSetup.getCall(0).args[0];
    expect(args).toEqual(projectJson);
  });

  it('should handle error on adding Codespaces failure', async () => {
    const alertBus = stubAlertBus();
    const projectService = stubProjectService();
    projectService.addGitpodSetup.rejects('error');
    await wrap({ alertBus, projectService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addGitpodSetup();

    const [message] = alertBus.error.getCall(0).args;
    expect(message).toBe('Adding Gitpod setup to project failed error');
  });

  it('should not add JaCoCo when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addJaCoCo.resolves({});
    await wrap({ projectService, project: createProjectToUpdate({ folder: '' }) });

    await component.addJaCoCo();

    expect(projectService.addJaCoCo.called).toBe(false);
  });

  it('should add JaCoCo when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addJaCoCo.resolves({});
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await wrap({ projectService, project: projectToUpdate });

    await component.addJaCoCo();

    const args = projectService.addJaCoCo.getCall(0).args[0];
    expect(args).toEqual(projectJson);
  });

  it('should handle error on adding JaCoCo failure', async () => {
    const alertBus = stubAlertBus();
    const projectService = stubProjectService();
    projectService.addJaCoCo.rejects('error');
    await wrap({ alertBus, projectService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addJaCoCo();

    const [message] = alertBus.error.getCall(0).args;
    expect(message).toBe('Adding JaCoCo to project failed error');
  });

  it('should not add Sonar Backend when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addSonarBackend.resolves({});
    await wrap({ projectService, project: createProjectToUpdate({ folder: '' }) });

    await component.addSonarBackend();

    expect(projectService.addSonarBackend.called).toBe(false);
  });

  it('should add Sonar Backend when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addSonarBackend.resolves({});
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await wrap({ projectService, project: projectToUpdate });

    await component.addSonarBackend();

    const args = projectService.addSonarBackend.getCall(0).args[0];
    expect(args).toEqual(projectJson);
  });

  it('should handle error on adding Sonar Backend failure', async () => {
    const alertBus = stubAlertBus();
    const projectService = stubProjectService();
    projectService.addSonarBackend.rejects('error');
    await wrap({ alertBus, projectService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSonarBackend();

    const [message] = alertBus.error.getCall(0).args;
    expect(message).toBe('Adding Sonar Backend to project failed error');
  });

  it('should not add Sonar Backend+Frontend when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addSonarBackendFrontend.resolves({});
    await wrap({ projectService, project: createProjectToUpdate({ folder: '' }) });

    await component.addSonarBackendFrontend();

    expect(projectService.addSonarBackendFrontend.called).toBe(false);
  });

  it('should add Sonar Backend+Frontend when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addSonarBackendFrontend.resolves({});
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await wrap({ projectService, project: projectToUpdate });

    await component.addSonarBackendFrontend();

    const args = projectService.addSonarBackendFrontend.getCall(0).args[0];
    expect(args).toEqual(projectJson);
  });

  it('should handle error on adding Sonar Backend+Frontend failure', async () => {
    const alertBus = stubAlertBus();
    const projectService = stubProjectService();
    projectService.addSonarBackendFrontend.rejects('error');
    await wrap({ alertBus, projectService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addSonarBackendFrontend();

    const [message] = alertBus.error.getCall(0).args;
    expect(message).toBe('Adding Sonar Backend+Frontend to project failed error');
  });

  it('should not add JavaBase when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addJavaBase.resolves({});
    await wrap({ projectService, project: createProjectToUpdate({ folder: '' }) });

    await component.addJavaBase();

    expect(projectService.addJavaBase.called).toBe(false);
  });

  it('should add JavaBase when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addJavaBase.resolves({});
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await wrap({ projectService, project: projectToUpdate });

    await component.addJavaBase();

    const args = projectService.addJavaBase.getCall(0).args[0];
    expect(args).toEqual(projectJson);
  });

  it('should handle error on adding java base failure', async () => {
    const alertBus = stubAlertBus();
    const projectService = stubProjectService();
    projectService.addJavaBase.rejects('error');
    await wrap({ alertBus, projectService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addJavaBase();

    const [message] = alertBus.error.getCall(0).args;
    expect(message).toBe('Adding Java Base to project failed error');
  });

  it('should not add Frontend Maven Plugin when project path is not filled', async () => {
    const projectService = stubProjectService();
    projectService.addFrontendMavenPlugin.resolves({});
    await wrap({ projectService, project: createProjectToUpdate({ folder: '' }) });

    await component.addFrontendMavenPlugin();

    expect(projectService.addFrontendMavenPlugin.called).toBe(false);
  });

  it('should add Frontend Maven Plugin when project path is filled', async () => {
    const projectService = stubProjectService();
    projectService.addFrontendMavenPlugin.resolves({});
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await wrap({ projectService, project: projectToUpdate });

    await component.addFrontendMavenPlugin();

    const args = projectService.addFrontendMavenPlugin.getCall(0).args[0];
    expect(args).toEqual(projectJson);
  });

  it('should handle error on adding Frontend Maven Plugin failure', async () => {
    const alertBus = stubAlertBus();
    const projectService = stubProjectService();
    projectService.addFrontendMavenPlugin.rejects('error');
    await wrap({ alertBus, projectService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.addFrontendMavenPlugin();

    const [message] = alertBus.error.getCall(0).args;
    expect(message).toBe('Adding Frontend Maven Plugin to project failed error');
  });

  it('should download initialized project with basename', async () => {
    const projectService = stubProjectService();
    projectService.download.resolves({});
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await wrap({ projectService, project: projectToUpdate });

    await component.download();

    const args = projectService.download.getCall(0).args[0];
    expect(args).toEqual(projectJson);
  });

  it('should download initialized project without basename', async () => {
    const projectService = stubProjectService();
    projectService.download.resolves({});
    const projectToUpdate: ProjectToUpdate = createProjectToUpdate({
      folder: 'project/path',
      baseName: 'beer',
      projectName: 'Beer Project',
      packageName: 'tech.jhipster.beer',
      serverPort: '8080',
    });
    await wrap({ projectService, project: projectToUpdate });

    await component.download();

    const args = projectService.download.getCall(0).args[0];
    expect(args).toEqual(projectJson);
  });

  it('should not download an non existing project', async () => {
    const alertBus = stubAlertBus();
    const projectService = stubProjectService();
    projectService.download.rejects('error');
    await wrap({ alertBus, projectService, project: createProjectToUpdate({ folder: 'project/path' }) });

    await component.download();

    const [message] = alertBus.error.getCall(0).args;
    expect(message).toBe('Downloading project failed error');
  });
});
