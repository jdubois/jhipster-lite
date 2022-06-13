package tech.jhipster.lite.generator.server.springboot.dbmigration.mongock.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TECHNICAL_INFRASTRUCTURE_SECONDARY;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_PATH;

import java.util.TreeMap;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

public class MongockDomainService implements MongockService {

  public static final String SOURCE = "server/springboot/database/mongock";

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;
  private final SpringBootCommonService springBootCommonService;

  public MongockDomainService(
    BuildToolService buildToolService,
    ProjectRepository projectRepository,
    SpringBootCommonService springBootCommonService
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springBootCommonService = springBootCommonService;
  }

  @Override
  public void init(Project project) {
    addMongockDependency(project);
    addConfigurationJava(project);
    addChangelogJava(project);
    addProperties(project);
  }

  @Override
  public void addMongockDependency(Project project) {
    String mongockDependencyGroupId = "io.mongock";
    buildToolService
      .getVersion(project, "mongock")
      .ifPresentOrElse(
        version -> buildToolService.addProperty(project, "mongock.version", version),
        () -> {
          throw new GeneratorException("Version not found: mongock");
        }
      );

    Dependency mongockBomDependency = Dependency
      .builder()
      .groupId(mongockDependencyGroupId)
      .artifactId("mongock-bom")
      .version("\\${mongock.version}")
      .scope("import")
      .type("pom")
      .build();
    buildToolService.addDependencyManagement(project, mongockBomDependency);

    Dependency mongockSpringDependency = Dependency.builder().groupId(mongockDependencyGroupId).artifactId("mongock-springboot").build();
    buildToolService.addDependency(project, mongockSpringDependency);

    Dependency mongockSpringDataDriverDependency = Dependency
      .builder()
      .groupId(mongockDependencyGroupId)
      .artifactId("mongodb-springdata-v3-driver")
      .build();
    buildToolService.addDependency(project, mongockSpringDataDriverDependency);
  }

  @Override
  public void addConfigurationJava(Project project) {
    String packageNamePath = project.getPackageNamePath().orElse(PACKAGE_PATH);
    String mongockConfigPath = TECHNICAL_INFRASTRUCTURE_SECONDARY + "/mongock";
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "MongockDatabaseConfiguration.java")
        .withDestinationFolder(getPath(MAIN_JAVA, packageNamePath, mongockConfigPath))
    );
  }

  @Override
  public void addChangelogJava(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(PACKAGE_PATH);
    String mongockDBMigrationPath = TECHNICAL_INFRASTRUCTURE_SECONDARY + "/mongock/dbmigration";

    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "InitialMigrationSetup.java")
        .withDestinationFolder(getPath(MAIN_JAVA, packageNamePath, mongockDBMigrationPath))
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "InitialMigrationSetupTest.java")
        .withDestinationFolder(getPath(TEST_JAVA, packageNamePath, mongockDBMigrationPath))
    );
  }

  @Override
  public void addProperties(Project project) {
    getMongockProperties(project).forEach((k, v) -> springBootCommonService.addProperties(project, k, v));
    springBootCommonService.addPropertiesTest(project, "mongock.enabled", false);
  }

  private TreeMap<String, Object> getMongockProperties(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packageName = project.getPackageName().orElse("com.mycompany.app");
    String mongockpackage = packageName + ".technical.infrastructure.secondary.mongock.dbmigration";
    TreeMap<String, Object> result = new TreeMap<>();

    result.put("mongock.migration-scan-package", mongockpackage);

    return result;
  }
}
