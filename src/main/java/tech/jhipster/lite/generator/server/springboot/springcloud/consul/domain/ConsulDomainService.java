package tech.jhipster.lite.generator.server.springboot.springcloud.consul.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;
import static tech.jhipster.lite.generator.server.springboot.springcloud.consul.domain.Consul.*;

import tech.jhipster.lite.common.domain.Base64Utils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;

public class ConsulDomainService implements ConsulService {

  public static final String SOURCE = "server/springboot/springcloud/consul";

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;
  private final SpringCloudCommonService springCloudCommonService;
  private final DockerService dockerService;

  public ConsulDomainService(
    BuildToolService buildToolService,
    ProjectRepository projectRepository,
    SpringCloudCommonService springCloudCommonService,
    DockerService dockerService
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springCloudCommonService = springCloudCommonService;
    this.dockerService = dockerService;
  }

  @Override
  public void init(Project project) {
    addDependencies(project);
    addProperties(project);
    addDockerConsul(project);
  }

  @Override
  public void addDependencies(Project project) {
    this.buildToolService.getVersion(project, "spring-cloud")
      .ifPresentOrElse(
        version -> {
          springCloudCommonService.addSpringCloudCommonDependencies(project);
          buildToolService.addDependency(project, springCloudConsulConfigDependency());
          buildToolService.addDependency(project, springCloudConsulDiscoveryDependency());
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
      getPath(SOURCE, "src"),
      BOOTSTRAP_PROPERTIES,
      getPath(MAIN_RESOURCES, CONFIG)
    );
    springCloudCommonService.addOrMergeBootstrapProperties(
      project,
      getPath(SOURCE, "src"),
      BOOTSTRAP_LOCAL_PROPERTIES,
      getPath(MAIN_RESOURCES, CONFIG)
    );
    springCloudCommonService.addOrMergeBootstrapProperties(
      project,
      getPath(SOURCE, "test"),
      BOOTSTRAP_PROPERTIES,
      getPath(TEST_RESOURCES, CONFIG)
    );
  }

  @Override
  public void addDockerConsul(Project project) {
    String consulImage = dockerService
      .getImageNameWithVersion(getDockerConsulImageName())
      .orElseThrow(() -> new GeneratorException("Version not found for docker image: " + getDockerConsulImageName()));

    String consulConfigLoaderImage = dockerService
      .getImageNameWithVersion(getDockerConsulConfigLoaderImageName())
      .orElseThrow(() -> new GeneratorException("Version not found for docker image: " + getDockerConsulConfigLoaderImageName()));

    project.addConfig("dockerConsulImage", consulImage);
    project.addConfig("dockerConsulConfigLoaderImage", consulConfigLoaderImage);

    projectRepository.template(
      ProjectFile.forProject(project).withSource(getPath(SOURCE, "src"), "consul.yml").withDestination("src/main/docker", "consul.yml")
    );

    project.addConfig("base64JwtSecret", Base64Utils.getBase64Secret());
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, "docker"), "application.config.yml")
        .withDestination("src/main/docker/central-server-config/", "application.yml")
    );
  }
}
