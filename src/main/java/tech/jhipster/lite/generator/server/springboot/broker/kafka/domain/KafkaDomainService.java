package tech.jhipster.lite.generator.server.springboot.broker.kafka.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;
import static tech.jhipster.lite.generator.server.springboot.broker.kafka.domain.Akhq.*;
import static tech.jhipster.lite.generator.server.springboot.broker.kafka.domain.Kafka.*;
import static tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommon.*;

import java.util.TreeMap;
import tech.jhipster.lite.common.domain.WordUtils;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.DefaultConfig;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.readme.domain.ReadMeService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

public class KafkaDomainService implements KafkaService {

  private static final String SOURCE = "server/springboot/broker/kafka";
  private static final String DUMMY_TOPIC_NAME = "kafka.topic.dummy";
  private static final String DUMMY_PRODUCER_PATH = "dummy/infrastructure/secondary/kafka/producer";
  private static final String DUMMY_CONSUMER_PATH = "dummy/infrastructure/primary/kafka/consumer";

  private final BuildToolService buildToolService;

  private final ProjectRepository projectRepository;

  private final SpringBootCommonService springBootCommonService;

  private final DockerImages dockerImages;

  private final ReadMeService readMeService;

  public KafkaDomainService(
    final BuildToolService buildToolService,
    final ProjectRepository projectRepository,
    final SpringBootCommonService springBootCommonService,
    final DockerImages dockerImages,
    final ReadMeService readMeService
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springBootCommonService = springBootCommonService;
    this.dockerImages = dockerImages;
    this.readMeService = readMeService;
  }

  @Override
  public void init(final Project project) {
    addApacheKafkaClient(project);
    addDockerCompose(project);
    addProperties(project);
    addTestcontainers(project);
    addConfiguration(project);
    addReadMeSection(project);
  }

  private void addConfiguration(final Project project) {
    final String packageNamePath = project.getPackageNamePath().orElse(getPath(DefaultConfig.PACKAGE_PATH));
    final String configKafkaPath = TECHNICAL_INFRASTRUCTURE_CONFIG + "/kafka";

    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "KafkaProperties.java")
        .withDestinationFolder(getPath(MAIN_JAVA, packageNamePath, configKafkaPath))
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "KafkaPropertiesTest.java")
        .withDestinationFolder(getPath(TEST_JAVA, packageNamePath, configKafkaPath))
    );

    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "KafkaConfiguration.java")
        .withDestinationFolder(getPath(MAIN_JAVA, packageNamePath, TECHNICAL_INFRASTRUCTURE_CONFIG + "/kafka"))
    );
  }

  @Override
  public void addDummyProducerConsumer(final Project project) {
    if (springBootCommonService.getProperty(project, DUMMY_TOPIC_NAME).isEmpty()) {
      project.addDefaultConfig(PACKAGE_NAME);
      project.addDefaultConfig(BASE_NAME);
      final String packageNamePath = project.getPackageNamePath().orElse(getPath(DefaultConfig.PACKAGE_PATH));

      final String topicName = "queue." + project.getBaseName().orElse("jhipster") + ".dummy";
      springBootCommonService.addProperties(project, DUMMY_TOPIC_NAME, topicName);
      springBootCommonService.addPropertiesTest(project, DUMMY_TOPIC_NAME, topicName);

      projectRepository.template(
        ProjectFile
          .forProject(project)
          .withSource(SOURCE, "DummyProducer.java")
          .withDestinationFolder(getPath(MAIN_JAVA, packageNamePath, DUMMY_PRODUCER_PATH))
      );
      projectRepository.template(
        ProjectFile
          .forProject(project)
          .withSource(SOURCE, "DummyProducerTest.java")
          .withDestinationFolder(getPath(TEST_JAVA, packageNamePath, DUMMY_PRODUCER_PATH))
      );

      projectRepository.template(
        ProjectFile
          .forProject(project)
          .withSource(SOURCE, "DummyProducerIT.java")
          .withDestinationFolder(getPath(TEST_JAVA, packageNamePath, DUMMY_PRODUCER_PATH))
      );

      projectRepository.template(
        ProjectFile
          .forProject(project)
          .withSource(SOURCE, "AbstractConsumer.java")
          .withDestinationFolder(getPath(MAIN_JAVA, packageNamePath, DUMMY_CONSUMER_PATH))
      );
      projectRepository.template(
        ProjectFile
          .forProject(project)
          .withSource(SOURCE, "DummyConsumer.java")
          .withDestinationFolder(getPath(MAIN_JAVA, packageNamePath, DUMMY_CONSUMER_PATH))
      );
      projectRepository.template(
        ProjectFile
          .forProject(project)
          .withSource(SOURCE, "DummyConsumerIT.java")
          .withDestinationFolder(getPath(TEST_JAVA, packageNamePath, DUMMY_CONSUMER_PATH))
      );
      projectRepository.template(
        ProjectFile
          .forProject(project)
          .withSource(SOURCE, "DummyConsumerTest.java")
          .withDestinationFolder(getPath(TEST_JAVA, packageNamePath, DUMMY_CONSUMER_PATH))
      );
    }
  }

  @Override
  public void addAkhq(final Project project) {
    final String akhqDockerImage = dockerImages.get(AKHQ_DOCKER_IMAGE).fullName();
    project.addConfig("akhqDockerImage", akhqDockerImage);
    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE, AKHQ_DOCKER_COMPOSE_FILE).withDestination(MAIN_DOCKER, AKHQ_DOCKER_COMPOSE_FILE)
    );
  }

  private void addApacheKafkaClient(final Project project) {
    final Dependency dependency = Dependency.builder().groupId("org.apache.kafka").artifactId("kafka-clients").build();
    buildToolService.addDependency(project, dependency);
  }

  private void addDockerCompose(final Project project) {
    final String zookeeperDockerImage = dockerImages.get(Zookeeper.ZOOKEEPER_DOCKER_IMAGE).fullName();
    final String kafkaDockerImage = dockerImages.get(KAFKA_DOCKER_IMAGE).fullName();

    project.addDefaultConfig(BASE_NAME);
    project.addConfig("zookeeperDockerImage", zookeeperDockerImage);
    project.addConfig("kafkaDockerImage", kafkaDockerImage);
    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE, KAFKA_DOCKER_COMPOSE_FILE).withDestination(MAIN_DOCKER, KAFKA_DOCKER_COMPOSE_FILE)
    );
  }

  private void addProperties(final Project project) {
    final String kebabBaseName = WordUtils.kebabCase(project.getBaseName().orElse(DEFAULT_BASE_NAME));
    getKafkaCommonProperties(kebabBaseName)
      .forEach((k, v) -> {
        springBootCommonService.addProperties(project, k, v);
        springBootCommonService.addPropertiesTest(project, k, v);
      });
  }

  private TreeMap<String, Object> getKafkaCommonProperties(final String kebabBaseName) {
    final TreeMap<String, Object> result = new TreeMap<>();

    result.put("# Kafka Configuration", "");
    result.put("kafka.bootstrap-servers", "localhost:9092");
    result.put("kafka.consumer.'[key.deserializer]'", "org.apache.kafka.common.serialization.StringDeserializer");
    result.put("kafka.consumer.'[value.deserializer]'", "org.apache.kafka.common.serialization.StringDeserializer");
    result.put("kafka.consumer.'[group.id]'", kebabBaseName);
    result.put("kafka.consumer.'[auto.offset.reset]'", "earliest");
    result.put("kafka.producer.'[key.serializer]'", "org.apache.kafka.common.serialization.StringSerializer");
    result.put("kafka.producer.'[value.serializer]'", "org.apache.kafka.common.serialization.StringSerializer");
    result.put("kafka.polling.timeout", "10000");

    return result;
  }

  void addTestcontainers(final Project project) {
    final String packageNamePath = project.getPackageNamePath().orElse(getPath(DefaultConfig.PACKAGE_PATH));
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "KafkaTestContainerExtension.java")
        .withDestinationFolder(getPath(TEST_JAVA, packageNamePath))
    );
    buildToolService.addVersionPropertyAndDependency(project, "testcontainers", testContainersDependency("kafka"));
    springBootCommonService.updateIntegrationTestAnnotation(project, "KafkaTestContainerExtension");
  }

  private void addReadMeSection(final Project project) {
    readMeService.addSection(
      project,
      "## Apache Kafka",
      """
      Before to launch `./mvnw spring-boot:run`, please launch the following command in the root directory: `docker-compose -f src/main/docker/kafka.yml up`.
      """
    );
  }
}
