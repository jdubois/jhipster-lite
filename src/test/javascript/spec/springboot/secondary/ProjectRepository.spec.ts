import { Project } from '@/springboot/domain/Project';
import ProjectRepository from '@/springboot/secondary/ProjectRepository';
import { stubAxiosHttp } from '../../http/AxiosHttpStub';
import { RestProject, toRestProject } from '@/springboot/secondary/RestProject';
import { createProject } from '../domain/Project.fixture';

describe('ProjectRepository', () => {
  it('should init project', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.init(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/projects/init');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });

  it('should add Maven', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addMaven(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/build-tools/maven');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });

  it('should add JavaBase', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addJavaBase(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/servers/java/base');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });

  it('should add SpringBoot', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addSpringBoot(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/servers/spring-boot');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });

  it('should add SpringBoot MVC with Tomcat', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addSpringBootMvcTomcat(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/servers/spring-boot/mvc/web/tomcat');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });

  it('should add Frontend Maven Plugin', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addFrontendMavenPlugin(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/frontend-maven-plugin');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });

  it('should add Ippon Banner', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addSpringBootBannerIppon(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/servers/spring-boot/banner/ippon');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });

  it('should add JHipster v2 Banner', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addSpringBootBannerJHipsterV2(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/servers/spring-boot/banner/jhipster-v2');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });

  it('should add JHipster v3 Banner', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addSpringBootBannerJHipsterV3(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/servers/spring-boot/banner/jhipster-v3');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });

  it('should add JHipster v7 Banner', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addSpringBootBannerJHipsterV7(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/servers/spring-boot/banner/jhipster-v7');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });

  it('should add JHipster v7 React Banner', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addSpringBootBannerJHipsterV7React(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/servers/spring-boot/banner/jhipster-v7-react');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });

  it('should add JHipster v7 Vue Banner', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addSpringBootBannerJHipsterV7Vue(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/servers/spring-boot/banner/jhipster-v7-vue');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });
  it('should add Postgres', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addPostgres(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('/api/servers/spring-boot/databases/postgresql');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });
  it('should add Mysql', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addMySQL(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('/api/servers/spring-boot/databases/mysql');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });
  it('should add MariaDB', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addMariaDB(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('/api/servers/spring-boot/databases/mariadb');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });
  it('should add MongoDB', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addMongoDB(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('/api/servers/spring-boot/databases/mongodb');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });
  it('should migrate flyway init', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addSpringBootFlywayInit(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('/api/servers/spring-boot/databases/migration/flyway/init');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });
  it('should migrate flyway user', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addSpringBootFlywayUser(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('/api/servers/spring-boot/databases/migration/flyway/user');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });
  it('should migrate liquibase init', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addSpringBootLiquibaseInit(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('/api/servers/spring-boot/databases/migration/liquibase/init');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });
  it('should migrate liquibase user', () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const projectRepository = new ProjectRepository(axiosHttpStub);
    const project: Project = createProject({ folder: 'folder/path' });

    projectRepository.addSpringBootLiquibaseUser(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('/api/servers/spring-boot/databases/migration/liquibase/user');
    expect(payload).toEqual<RestProject>(expectedRestProject);
  });
});
