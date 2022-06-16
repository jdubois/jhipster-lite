package tech.jhipster.lite.generator.server.springboot.broker.pulsar.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;
import static tech.jhipster.lite.generator.server.springboot.broker.pulsar.domain.Pulsar.*;
import static tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommon.*;

import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.docker.domain.DockerImages;
import tech.jhipster.lite.generator.project.domain.DefaultConfig;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

public class PulsarDomainService implements PulsarService {

  private static final String SOURCE = "server/springboot/broker/pulsar";

  private final BuildToolService buildToolService;

  private final ProjectRepository projectRepository;

  private final SpringBootCommonService springBootCommonService;

  private final DockerImages dockerImages;

  public PulsarDomainService(
    final BuildToolService buildToolService,
    final ProjectRepository projectRepository,
    final SpringBootCommonService springBootCommonService,
    DockerImages dockerImages
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springBootCommonService = springBootCommonService;
    this.dockerImages = dockerImages;
  }

  @Override
  public void init(final Project project) {
    addApachePulsarClient(project);
    addDockerCompose(project);
    addProperties(project);
    addTestcontainers(project);
    addConfiguration(project);
  }

  private void addConfiguration(final Project project) {
    final String packageNamePath = project.getPackageNamePath().orElse(getPath(DefaultConfig.PACKAGE_PATH));
    final String configPulsarPath = TECHNICAL_INFRASTRUCTURE_CONFIG + "/pulsar";

    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "PulsarProperties.java")
        .withDestinationFolder(getPath(MAIN_JAVA, packageNamePath, configPulsarPath))
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "PulsarConfiguration.java")
        .withDestinationFolder(getPath(MAIN_JAVA, packageNamePath, configPulsarPath))
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "PulsarConfigurationIT.java")
        .withDestinationFolder(getPath(TEST_JAVA, packageNamePath, configPulsarPath))
    );
  }

  private void addApachePulsarClient(final Project project) {
    final Dependency dependency = Dependency.builder().groupId("org.apache.pulsar").artifactId("pulsar-client").build();
    buildToolService.addVersionPropertyAndDependency(project, PULSAR_PROPERTY_VERSION, dependency);
  }

  private void addDockerCompose(final Project project) {
    String pulsarDockerImage = dockerImages.get(PULSAR_DOCKER_IMAGE).fullName();

    project.addDefaultConfig(BASE_NAME);
    project.addConfig("pulsarDockerImage", pulsarDockerImage);
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, PULSAR_DOCKER_COMPOSE_FILE)
        .withDestination(MAIN_DOCKER, PULSAR_DOCKER_COMPOSE_FILE)
    );
  }

  private void addProperties(final Project project) {
    springBootCommonService.addPropertiesComment(project, "Pulsar Configuration");
    springBootCommonService.addProperties(project, "pulsar.client.service-url", "pulsar://localhost:6650");
    springBootCommonService.addPropertiesNewLine(project);

    springBootCommonService.addPropertiesTestComment(project, "Pulsar Configuration");
    springBootCommonService.addPropertiesTest(project, "pulsar.client.num-io-threads", 8);
    springBootCommonService.addPropertiesTest(project, "pulsar.producer.topic-name", "test-topic");
    springBootCommonService.addPropertiesTest(project, "pulsar.consumer.topic-names[0]", "test-topic");
    springBootCommonService.addPropertiesTest(project, "pulsar.consumer.subscription-name", "test-subscription");
    springBootCommonService.addPropertiesTestNewLine(project);
  }

  void addTestcontainers(final Project project) {
    final String packageNamePath = project.getPackageNamePath().orElse(getPath(DefaultConfig.PACKAGE_PATH));
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "PulsarTestContainerExtension.java")
        .withDestinationFolder(getPath(TEST_JAVA, packageNamePath))
    );
    buildToolService.addVersionPropertyAndDependency(project, "testcontainers", testContainersDependency("pulsar"));
    springBootCommonService.updateIntegrationTestAnnotation(project, "PulsarTestContainerExtension");
  }
}
