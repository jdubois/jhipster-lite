package tech.jhipster.lite.generator.server.javatool.arch.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;

import java.util.Arrays;
import java.util.stream.Collectors;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

public class JavaArchUnitDomainService implements JavaArchUnitService {

  public static final String SOURCE = "server/javatool/arch";

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;

  public JavaArchUnitDomainService(
    ProjectRepository projectRepository,
    BuildToolService buildToolService,
    SpringBootCommonService springBootCommonService
  ) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
  }

  @Override
  public void init(Project project) {
    addHexagobalArchJavaFiles(project);
    addArchUnitMavenPlugin(project);
    addLoggerInConfiguration(project);
  }

  private void addHexagobalArchJavaFiles(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);

    String packageNamePath = project.getPackageNamePath().orElse(PACKAGE_PATH);
    String packageWalkPath = Arrays.stream(packageNamePath.split("/")).map(s -> "\"" + s + "\"").collect(Collectors.joining(", "));
    project.addConfig("packageWalkPath", packageWalkPath);

    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE, "archunit.properties").withDestinationFolder(getPath(TEST_RESOURCES))
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "HexagonalArchTest.java")
        .withDestinationFolder(getPath(TEST_JAVA, packageNamePath))
    );
  }

  private void addArchUnitMavenPlugin(Project project) {
    buildToolService
      .getVersion(project, "archunit-junit5")
      .ifPresentOrElse(
        version -> {
          buildToolService.addProperty(project, ArchUnit.ARCHUNIT_JUNIT5_VERSION, version);
          buildToolService.addDependency(project, ArchUnit.archUnitDependency());
        },
        () -> {
          throw new GeneratorException("Version not found: archunit-junit5");
        }
      );
  }

  private void addLoggerInConfiguration(Project project) {
    addLogger(project, "com.tngtech.archunit", Level.WARN);
  }

  private void addLogger(Project project, String packageName, Level level) {
    springBootCommonService.addLoggerTest(project, packageName, level);
  }
}
