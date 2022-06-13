package tech.jhipster.lite.generator.server.springboot.springcloud.gateway.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.CONFIG;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVA;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_RESOURCES;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_NAME;
import static tech.jhipster.lite.generator.server.springboot.springcloud.gateway.domain.Gateway.springCloudGateway;

import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;

public class GatewayDomainService implements GatewayService {

  public static final String SOURCE = "server/springboot/springcloud/gateway";

  public static final String BOOTSTRAP_PROPERTIES = "bootstrap.properties";

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;
  private final SpringCloudCommonService springCloudCommonService;

  public GatewayDomainService(
    BuildToolService buildToolService,
    ProjectRepository projectRepository,
    SpringCloudCommonService springCloudCommonService
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springCloudCommonService = springCloudCommonService;
  }

  @Override
  public void init(Project project) {
    addDependencies(project);
    addProperties(project);
    addJavaFiles(project);
  }

  @Override
  public void addDependencies(Project project) {
    this.buildToolService.getVersion(project, "spring-cloud")
      .ifPresentOrElse(
        version -> {
          springCloudCommonService.addSpringCloudCommonDependencies(project);
          buildToolService.addDependency(project, springCloudGateway());
        },
        () -> {
          throw new GeneratorException("Spring Cloud version not found");
        }
      );
  }

  @Override
  public void addProperties(Project project) {
    project.addDefaultConfig(BASE_NAME);
    String baseName = project.getBaseName().orElse(BASE_NAME);
    project.addConfig("baseNameLowercase", baseName.toLowerCase());

    springCloudCommonService.addOrMergeBootstrapProperties(
      project,
      getPath(SOURCE, "resources"),
      BOOTSTRAP_PROPERTIES,
      getPath(MAIN_RESOURCES, CONFIG)
    );
    springCloudCommonService.addOrMergeBootstrapProperties(
      project,
      getPath(SOURCE, "resources/test"),
      BOOTSTRAP_PROPERTIES,
      getPath(TEST_RESOURCES, CONFIG)
    );
  }

  @Override
  public void addJavaFiles(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(BASE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));
    String resourcePath = "technical/infrastructure/primary/rest";

    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, "java"), "GatewayResource.java")
        .withDestinationFolder(getPath(MAIN_JAVA, packageNamePath, resourcePath))
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, "java"), "RouteVM.java")
        .withDestinationFolder(getPath(MAIN_JAVA, packageNamePath, resourcePath + "/vm"))
    );

    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, "java/test"), "GatewayResourceIT.java")
        .withDestinationFolder(getPath(TEST_JAVA, packageNamePath, resourcePath))
    );
  }
}
