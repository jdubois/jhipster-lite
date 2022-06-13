import { Project } from '@/springboot/domain/Project';

export interface SpringBootService {
  addSpringBoot(project: Project): Promise<void>;

  addSpringBootMvcTomcat(project: Project): Promise<void>;
  addSpringBootMvcUndertow(project: Project): Promise<void>;
  addSpringBootWebfluxNetty(project: Project): Promise<void>;
  addSpringBootActuator(project: Project): Promise<void>;
  addSpringDoc(project: Project): Promise<void>;

  addJWT(project: Project): Promise<void>;
  addBasicAuthJWT(project: Project): Promise<void>;
  addOAuth2(project: Project): Promise<void>;
  addOAuth2Account(project: Project): Promise<void>;
  addSpringdocJWT(project: Project): Promise<void>;

  addSpringBootAopLogging(project: Project): Promise<void>;
  addSpringBootLogstash(project: Project): Promise<void>;

  addPostgres(project: Project): Promise<void>;
  addMySQL(project: Project): Promise<void>;
  addMariaDB(project: Project): Promise<void>;
  addMongoDB(project: Project): Promise<void>;
  addSpringBootFlywayInit(project: Project): Promise<void>;
  addSpringBootFlywayUser(project: Project): Promise<void>;
  addSpringBootLiquibaseInit(project: Project): Promise<void>;
  addSpringBootLiquibaseUser(project: Project): Promise<void>;
  addSpringBootMongockInit(project: Project): Promise<void>;

  addEhcacheWithJavaConf(project: Project): Promise<void>;
  addEhcacheWithXML(project: Project): Promise<void>;
  addSimpleCache(project: Project): Promise<void>;

  addSpringCloudConfigClient(project: Project): Promise<void>;
  addSpringCloudConsul(project: Project): Promise<void>;
  addSpringCloudEureka(project: Project): Promise<void>;

  addSpringBootAsync(project: Project): Promise<void>;
  addSpringBootDevtoolsDependencies(project: Project): Promise<void>;
  addSpringBootDockerfile(project: Project): Promise<void>;
  addSpringBootDockerJib(project: Project): Promise<void>;

  addSpringBootDummyFeature(project: Project): Promise<void>;

  addCucumber(project: Project): Promise<void>;
  addPulsar(project: Project): Promise<void>;
}
