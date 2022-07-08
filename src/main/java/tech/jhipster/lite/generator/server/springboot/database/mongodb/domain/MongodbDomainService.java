package tech.jhipster.lite.generator.server.springboot.database.mongodb.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;
import static tech.jhipster.lite.generator.server.springboot.database.mongodb.domain.Mongodb.*;

import java.util.Map;
import java.util.TreeMap;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.DatabaseType;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonService;

public class MongodbDomainService implements MongodbService {

  public static final String SOURCE = "server/springboot/database/mongodb";

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;
  private final SQLCommonService sqlCommonService;
  private final DockerImages dockerImages;

  public MongodbDomainService(
    ProjectRepository projectRepository,
    BuildToolService buildToolService,
    SpringBootCommonService springBootCommonService,
    SQLCommonService sqlCommonService,
    DockerImages dockerImages
  ) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
    this.sqlCommonService = sqlCommonService;
    this.dockerImages = dockerImages;
  }

  @Override
  public void init(Project project) {
    addSpringDataMongodb(project);
    addMongodbDriver(project);
    addDockerCompose(project);
    addJavaFiles(project);
    addConfigurationFiles(project);
    addProperties(project);
    addTestcontainers(project);
    addLoggerInConfiguration(project);

    updateIntegrationTestAnnotation(project);
  }

  @Override
  public void addSpringDataMongodb(Project project) {
    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-data-mongodb").build();

    buildToolService.addDependency(project, dependency);
  }

  @Override
  public void addMongodbDriver(Project project) {
    buildToolService.addDependency(project, mongodbDriver());
  }

  @Override
  public void addDockerCompose(Project project) {
    project.addDefaultConfig(BASE_NAME);

    String dockerImage = dockerImages.get(Mongodb.getMongodbDockerImageName()).fullName();
    project.addConfig("mongodbDockerImage", dockerImage);

    sqlCommonService.addDockerComposeTemplate(project, "mongodb");
  }

  @Override
  public void addJavaFiles(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(BASE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));
    String mongodbPath = "technical/infrastructure/secondary/mongodb";

    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "MongodbDatabaseConfiguration.java")
        .withDestinationFolder(getPath(MAIN_JAVA, packageNamePath, mongodbPath))
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "JSR310DateConverters.java")
        .withDestinationFolder(getPath(MAIN_JAVA, packageNamePath, mongodbPath))
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "JSR310DateConvertersTest.java")
        .withDestinationFolder(getPath(TEST_JAVA, packageNamePath, mongodbPath))
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "MongodbTestContainerExtension.java")
        .withDestinationFolder(getPath(TEST_JAVA, packageNamePath))
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "TestContainersSpringContextCustomizerFactory.java")
        .withDestinationFolder(getPath(TEST_JAVA, packageNamePath))
    );
  }

  @Override
  public void addConfigurationFiles(Project project) {
    project.addDefaultConfig(BASE_NAME);

    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE, "spring.factories").withDestinationFolder(getPath(TEST_RESOURCES, "META-INF"))
    );
  }

  @Override
  public void addProperties(Project project) {
    String baseName = project.getBaseName().orElse("jhipster");

    springBootCommonService.addPropertiesComment(project, "Database Configuration");

    springPropertiesMongodb(baseName).forEach((k, v) -> springBootCommonService.addProperties(project, k, v));
    springBootCommonService.addPropertiesNewLine(project);
  }

  private Map<String, Object> springPropertiesMongodb(String baseName) {
    TreeMap<String, Object> result = new TreeMap<>();

    result.put("spring.data.mongodb.database", baseName);
    result.put("spring.data.mongodb.uri", "mongodb://localhost:27017");

    return result;
  }

  private void addTestcontainers(Project project) {
    this.sqlCommonService.addTestcontainers(project, DatabaseType.MONGODB.id(), Map.of());
  }

  @Override
  public void addLoggerInConfiguration(Project project) {
    addLogger(project, "org.reflections", Level.WARN);
    addLogger(project, "org.mongodb.driver", Level.WARN);

    springBootCommonService.addLoggerTest(project, "com.github.dockerjava", Level.WARN);
    springBootCommonService.addLoggerTest(project, "org.testcontainers", Level.WARN);
  }

  public void addLogger(Project project, String packageName, Level level) {
    springBootCommonService.addLogger(project, packageName, level);
    springBootCommonService.addLoggerTest(project, packageName, level);
  }

  private void updateIntegrationTestAnnotation(Project project) {
    springBootCommonService.updateIntegrationTestAnnotation(project, "MongodbTestContainerExtension");
  }
}
