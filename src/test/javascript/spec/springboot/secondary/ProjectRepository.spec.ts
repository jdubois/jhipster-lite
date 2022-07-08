import { Project } from '@/springboot/domain/Project';
import ProjectRepository from '@/springboot/secondary/ProjectRepository';
import { stubAxiosHttp } from '../../http/AxiosHttpStub';
import { RestProject, toRestProject } from '@/springboot/secondary/RestProject';
import { createProject } from '../domain/Project.fixture';
import { stubProjectHistoryService } from '../../common/domain/ProjectHistoryService.fixture';
import { stubProjectStore } from '../primary/ProjectStore.fixture';

const PROJECT_FOLDER = 'folder/path';

describe('ProjectRepository', () => {
  it('should init project', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    const projectStoreStub = stubProjectStore();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub, projectHistoryService, projectStoreStub);
    const project: Project = createProject({ folder: PROJECT_FOLDER });

    await projectRepository.init(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/inits/full');
    expect(payload).toEqual<RestProject>(expectedRestProject);
    const [projectFolder] = projectHistoryService.get.getCall(0).args;
    expect(projectFolder).toBe(PROJECT_FOLDER);
  });

  it('should add Maven', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    const projectStoreStub = stubProjectStore();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub, projectHistoryService, projectStoreStub);
    const project: Project = createProject({ folder: PROJECT_FOLDER });

    await projectRepository.addMaven(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/build-tools/maven');
    expect(payload).toEqual<RestProject>(expectedRestProject);
    const [projectFolder] = projectHistoryService.get.getCall(0).args;
    expect(projectFolder).toBe(PROJECT_FOLDER);
  });

  it('should add Codespaces setup', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    const projectStoreStub = stubProjectStore();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub, projectHistoryService, projectStoreStub);
    const project: Project = createProject({ folder: PROJECT_FOLDER });

    await projectRepository.addCodespacesSetup(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/developer-tools/codespaces');
    expect(payload).toEqual<RestProject>(expectedRestProject);
    const [projectFolder] = projectHistoryService.get.getCall(0).args;
    expect(projectFolder).toBe(PROJECT_FOLDER);
  });

  it('should add Gitpod setup', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    const projectStoreStub = stubProjectStore();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub, projectHistoryService, projectStoreStub);
    const project: Project = createProject({ folder: PROJECT_FOLDER });

    await projectRepository.addGitpodSetup(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/developer-tools/gitpod');
    expect(payload).toEqual<RestProject>(expectedRestProject);
    const [projectFolder] = projectHistoryService.get.getCall(0).args;
    expect(projectFolder).toBe(PROJECT_FOLDER);
  });

  it('should add JaCoCo', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    const projectStoreStub = stubProjectStore();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub, projectHistoryService, projectStoreStub);
    const project: Project = createProject({ folder: PROJECT_FOLDER });

    await projectRepository.addJaCoCo(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/servers/java/jacoco-minimum-coverage');
    expect(payload).toEqual<RestProject>(expectedRestProject);
    const [projectFolder] = projectHistoryService.get.getCall(0).args;
    expect(projectFolder).toBe(PROJECT_FOLDER);
  });

  it('should add Sonar Backend', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    const projectStoreStub = stubProjectStore();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub, projectHistoryService, projectStoreStub);
    const project: Project = createProject({ folder: PROJECT_FOLDER });

    await projectRepository.addSonarBackend(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/developer-tools/sonar/java-backend');
    expect(payload).toEqual<RestProject>(expectedRestProject);
    const [projectFolder] = projectHistoryService.get.getCall(0).args;
    expect(projectFolder).toBe(PROJECT_FOLDER);
  });

  it('should add Sonar Backend+Frontend', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    const projectStoreStub = stubProjectStore();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub, projectHistoryService, projectStoreStub);
    const project: Project = createProject({ folder: PROJECT_FOLDER });

    await projectRepository.addSonarBackendFrontend(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/developer-tools/sonar/java-backend-and-frontend');
    expect(payload).toEqual<RestProject>(expectedRestProject);
    const [projectFolder] = projectHistoryService.get.getCall(0).args;
    expect(projectFolder).toBe(PROJECT_FOLDER);
  });

  it('should add JavaBase', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    const projectStoreStub = stubProjectStore();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub, projectHistoryService, projectStoreStub);
    const project: Project = createProject({ folder: PROJECT_FOLDER });

    await projectRepository.addJavaBase(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/servers/java/base');
    expect(payload).toEqual<RestProject>(expectedRestProject);
    const [projectFolder] = projectHistoryService.get.getCall(0).args;
    expect(projectFolder).toBe(PROJECT_FOLDER);
  });

  it('should add Frontend Maven Plugin', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    const projectStoreStub = stubProjectStore();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub, projectHistoryService, projectStoreStub);
    const project: Project = createProject({ folder: PROJECT_FOLDER });

    await projectRepository.addFrontendMavenPlugin(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/developer-tools/frontend-maven-plugin');
    expect(payload).toEqual<RestProject>(expectedRestProject);
    const [projectFolder] = projectHistoryService.get.getCall(0).args;
    expect(projectFolder).toBe(PROJECT_FOLDER);
  });
  it('should download the project', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    const projectStoreStub = stubProjectStore();
    axiosHttpStub.post.resolves({ headers: [], data: [1, 2, 3] });
    const projectRepository = new ProjectRepository(axiosHttpStub, projectHistoryService, projectStoreStub);
    const project: Project = createProject({ folder: PROJECT_FOLDER });

    const data = await projectRepository.download(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;

    expect(data.file).toEqual([1, 2, 3]);
    expect(uri).toBe('api/projects/download');
    const expectedRestProject: RestProject = toRestProject(project);
    expect(payload).toEqual<RestProject>(expectedRestProject);
    const [projectFolder] = projectHistoryService.get.getCall(0).args;
    expect(projectFolder).toBe(PROJECT_FOLDER);
  });

  it('should get project details', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    const projectStoreStub = stubProjectStore();
    const project: Project = createProject({ folder: PROJECT_FOLDER });
    axiosHttpStub.get.resolves({ data: toRestProject(project) });
    const projectRepository = new ProjectRepository(axiosHttpStub, projectHistoryService, projectStoreStub);

    await projectRepository.getProjectDetails(PROJECT_FOLDER);

    const [uri, params] = axiosHttpStub.get.getCall(0).args;
    expect(uri).toBe('api/projects/details');
    expect(params).toEqual({ params: { folder: PROJECT_FOLDER } });
    const [projectDetails] = projectStoreStub.setProject.getCall(0).args;
    expect(projectDetails).toEqual(project);
  });

  it('should set empty project details on api failure', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    const projectStoreStub = stubProjectStore();

    axiosHttpStub.get.rejects();
    const projectRepository = new ProjectRepository(axiosHttpStub, projectHistoryService, projectStoreStub);

    await projectRepository.getProjectDetails(PROJECT_FOLDER);

    const [uri, params] = axiosHttpStub.get.getCall(0).args;
    expect(uri).toBe('api/projects/details');
    expect(params).toEqual({ params: { folder: PROJECT_FOLDER } });
    const [projectDetails] = projectStoreStub.setProject.getCall(0).args;
    expect(projectDetails).toEqual({ folder: PROJECT_FOLDER });
  });

  it('should add Cypress', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    const projectStoreStub = stubProjectStore();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub, projectHistoryService, projectStoreStub);
    const project: Project = createProject({ folder: PROJECT_FOLDER });

    await projectRepository.addCypress(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/clients/cypress');
    expect(payload).toEqual<RestProject>(expectedRestProject);
    const [projectFolder] = projectHistoryService.get.getCall(0).args;
    expect(projectFolder).toBe(PROJECT_FOLDER);
  });

  it('should add Playwright', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    const projectStoreStub = stubProjectStore();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub, projectHistoryService, projectStoreStub);
    const project: Project = createProject({ folder: PROJECT_FOLDER });

    await projectRepository.addPlaywright(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/clients/playwright');
    expect(payload).toEqual<RestProject>(expectedRestProject);
    const [projectFolder] = projectHistoryService.get.getCall(0).args;
    expect(projectFolder).toBe(PROJECT_FOLDER);
  });
});
