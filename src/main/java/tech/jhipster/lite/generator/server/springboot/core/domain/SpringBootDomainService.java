package tech.jhipster.lite.generator.server.springboot.core.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.*;

import tech.jhipster.lite.common.domain.WordUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class SpringBootDomainService implements SpringBootService {

  public static final String SOURCE = "server/springboot/core";
  public static final String SPRINGBOOT_PACKAGE = "org.springframework.boot";

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;

  public SpringBootDomainService(ProjectRepository projectRepository, BuildToolService buildToolService) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
  }

  @Override
  public void init(Project project) {
    addSpringBootDependenciesBOM(project);
    addSpringBootDependencies(project);
    addSpringBootMavenPluginManagement(project);
    addSpringBootMavenPlugin(project);
    addMainApp(project);
    addApplicationProperties(project);
    addApplicationLocalProperties(project);
    addApplicationTestProperties(project);
    addLoggingConfiguration(project);
    addLoggingTestConfiguration(project);
  }

  @Override
  public void addSpringBootDependenciesBOM(Project project) {
    this.buildToolService.getVersion(project, "spring-boot")
      .ifPresentOrElse(
        version -> {
          project.addConfig("springBootVersion", version);

          Dependency springBootDependenciesPom = Dependency
            .builder()
            .groupId(SPRINGBOOT_PACKAGE)
            .artifactId("spring-boot-dependencies")
            .version("\\${spring-boot.version}")
            .type("pom")
            .scope("import")
            .build();

          buildToolService.addDependencyManagement(project, springBootDependenciesPom);
        },
        () -> {
          throw new GeneratorException("Spring Boot version not found");
        }
      );
  }

  @Override
  public void addSpringBootDependencies(Project project) {
    Dependency springBootStarterDependency = Dependency.builder().groupId(SPRINGBOOT_PACKAGE).artifactId("spring-boot-starter").build();
    buildToolService.addDependency(project, springBootStarterDependency);

    Dependency springBootConfigurationProcessor = Dependency
      .builder()
      .groupId(SPRINGBOOT_PACKAGE)
      .artifactId("spring-boot-configuration-processor")
      .optional()
      .build();
    buildToolService.addDependency(project, springBootConfigurationProcessor);

    Dependency commonLangDependency = Dependency.builder().groupId("org.apache.commons").artifactId("commons-lang3").build();
    buildToolService.addDependency(project, commonLangDependency);

    Dependency springBootStarterTestDependency = Dependency
      .builder()
      .groupId(SPRINGBOOT_PACKAGE)
      .artifactId("spring-boot-starter-test")
      .scope("test")
      .build();
    buildToolService.addDependency(project, springBootStarterTestDependency);
  }

  @Override
  public void addSpringBootMavenPluginManagement(Project project) {
    this.buildToolService.getVersion(project, "spring-boot")
      .ifPresentOrElse(
        version -> {
          Plugin plugin = Plugin
            .builder()
            .groupId(SPRINGBOOT_PACKAGE)
            .artifactId("spring-boot-maven-plugin")
            .version("\\${spring-boot.version}")
            .additionalElements(springBootAdditionalElements())
            .build();
          buildToolService.addProperty(project, "spring-boot.version", version);
          buildToolService.addPluginManagement(project, plugin);
        },
        () -> {
          throw new GeneratorException("Spring Boot version not found");
        }
      );
  }

  private String springBootAdditionalElements() {
    return """
        <executions>
          <execution>
            <goals>
              <goal>repackage</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <mainClass>\\${start-class}</mainClass>
        </configuration>
        """;
  }

  @Override
  public void addSpringBootMavenPlugin(Project project) {
    Plugin plugin = Plugin.builder().groupId(SPRINGBOOT_PACKAGE).artifactId("spring-boot-maven-plugin").build();
    buildToolService.addPlugin(project, plugin);
  }

  @Override
  public void addMainApp(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(BASE_NAME);

    String baseName = project.getBaseName().orElse("jhipster");
    String className = WordUtils.upperFirst(baseName);
    project.addConfig("mainClass", className);

    String packageNamePath = project.getPackageNamePath().orElse("com/mycompany/myapp");

    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "MainApp.java")
        .withDestination(getPath(MAIN_JAVA, packageNamePath), className + "App.java")
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "MainAppTest.java")
        .withDestination(getPath(TEST_JAVA, packageNamePath), className + "AppTest.java")
    );
    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE, "IntegrationTest.java").withDestinationFolder(getPath(TEST_JAVA, packageNamePath))
    );
  }

  @Override
  public void addApplicationProperties(Project project) {
    project.addDefaultConfig(BASE_NAME);

    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE, "application.properties").withDestinationFolder(getPath(MAIN_RESOURCES, CONFIG))
    );
  }

  @Override
  public void addApplicationLocalProperties(Project project) {
    project.addDefaultConfig(BASE_NAME);

    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "application-local.properties")
        .withDestinationFolder(getPath(MAIN_RESOURCES, CONFIG))
    );
  }

  @Override
  public void addApplicationTestProperties(Project project) {
    project.addDefaultConfig(BASE_NAME);

    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "application-test.properties")
        .withDestination(getPath(TEST_RESOURCES, CONFIG), "application.properties")
    );
  }

  @Override
  public void addLoggingConfiguration(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);

    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE, LOGGING_CONFIGURATION).withDestinationFolder(getPath(MAIN_RESOURCES))
    );
  }

  @Override
  public void addLoggingTestConfiguration(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);

    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE, LOGGING_TEST_CONFIGURATION).withDestinationFolder(getPath(TEST_RESOURCES))
    );
  }
}
