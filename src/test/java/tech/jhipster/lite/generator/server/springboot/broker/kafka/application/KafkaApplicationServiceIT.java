package tech.jhipster.lite.generator.server.springboot.broker.kafka.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.server.springboot.broker.kafka.domain.Akhq.AKHQ_DOCKER_COMPOSE_FILE;
import static tech.jhipster.lite.generator.server.springboot.broker.kafka.domain.Kafka.KAFKA_DOCKER_COMPOSE_FILE;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.APPLICATION_PROPERTIES;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;

@IntegrationTest
class KafkaApplicationServiceIT {

  @Autowired
  KafkaApplicationService kafkaApplicationService;

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Autowired
  DockerService dockerService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    kafkaApplicationService.init(project);
    assertFileContent(project, POM_XML, kafkaClients());

    String pathToKafkaDockerComposeFile = MAIN_DOCKER + "/" + KAFKA_DOCKER_COMPOSE_FILE;
    assertFileExist(project, pathToKafkaDockerComposeFile);
    assertFileContent(project, pathToKafkaDockerComposeFile, "KAFKA_BROKER_ID: 1");

    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "# Kafka Configuration");
    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "kafka.bootstrap-servers=localhost:9092");
    assertFileContent(project, POM_XML, "<testcontainers.version>");
    assertFileContent(project, POM_XML, "</testcontainers.version>");
    assertFileContent(project, POM_XML, testcontainers());

    String packagePath = getPath("com/mycompany/myapp");
    String configKafkaPath = getPath(packagePath, "technical/infrastructure/config/kafka");
    assertFileExist(project, getPath(MAIN_JAVA, configKafkaPath, "KafkaProperties.java"));
    assertFileContent(project, getPath(MAIN_JAVA, configKafkaPath, "KafkaProperties.java"), "public class KafkaProperties");

    String kafkaPropertiesTestPath = getPath("com/mycompany/myapp/technical/infrastructure/config/kafka");
    assertFileExist(project, getPath(TEST_JAVA, kafkaPropertiesTestPath, "KafkaPropertiesTest.java"));
    assertFileContent(project, getPath(TEST_JAVA, kafkaPropertiesTestPath, "KafkaPropertiesTest.java"), "class KafkaPropertiesTest");

    assertFileExist(project, getPath(MAIN_JAVA, configKafkaPath, "KafkaConfiguration.java"));
    assertFileContent(project, getPath(MAIN_JAVA, configKafkaPath, "KafkaConfiguration.java"), "public class KafkaConfiguration");

    assertFileExist(project, getPath(TEST_JAVA, packagePath, "KafkaTestContainerExtension.java"));
    assertFileContent(
      project,
      getPath(TEST_JAVA, packagePath, "KafkaTestContainerExtension.java"),
      "public class KafkaTestContainerExtension"
    );
  }

  @Test
  void shouldAddProducerConsumer() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);
    kafkaApplicationService.init(project);

    kafkaApplicationService.addDummyProducerConsumer(project);

    String dummyProducerPath = getPath("com/mycompany/myapp/dummy/infrastructure/secondary/kafka/producer");
    assertFileExist(project, getPath(MAIN_JAVA, dummyProducerPath, "DummyProducer.java"));
    assertFileContent(project, getPath(MAIN_JAVA, dummyProducerPath, "DummyProducer.java"), "public class DummyProducer");

    String dummyProducerTestPath = getPath("com/mycompany/myapp/dummy/infrastructure/secondary/kafka/producer");
    assertFileExist(project, getPath(TEST_JAVA, dummyProducerTestPath, "DummyProducerTest.java"));
    assertFileContent(project, getPath(TEST_JAVA, dummyProducerTestPath, "DummyProducerTest.java"), "class DummyProducerTest");

    assertFileExist(project, getPath(TEST_JAVA, dummyProducerTestPath, "DummyProducerIT.java"));
    assertFileContent(project, getPath(TEST_JAVA, dummyProducerTestPath, "DummyProducerIT.java"), "class DummyProducerIT");

    String dummyConsumerPath = getPath("com/mycompany/myapp/dummy/infrastructure/primary/kafka/consumer");
    assertFileExist(project, getPath(MAIN_JAVA, dummyConsumerPath, "AbstractConsumer.java"));
    assertFileContent(
      project,
      getPath(MAIN_JAVA, dummyConsumerPath, "AbstractConsumer.java"),
      "public abstract class AbstractConsumer<T> implements Runnable"
    );

    assertFileExist(project, getPath(MAIN_JAVA, dummyConsumerPath, "DummyConsumer.java"));
    assertFileContent(
      project,
      getPath(MAIN_JAVA, dummyConsumerPath, "DummyConsumer.java"),
      "public class DummyConsumer extends AbstractConsumer<String>"
    );

    assertFileExist(project, getPath(TEST_JAVA, dummyConsumerPath, "DummyConsumerIT.java"));
    assertFileContent(project, getPath(TEST_JAVA, dummyConsumerPath, "DummyConsumerIT.java"), "class DummyConsumerIT");

    assertFileExist(project, getPath(TEST_JAVA, dummyConsumerPath, "DummyConsumerTest.java"));
    assertFileContent(project, getPath(TEST_JAVA, dummyConsumerPath, "DummyConsumerTest.java"), "class DummyConsumerTest");
  }

  @Test
  void shouldAddAkhq() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);
    kafkaApplicationService.init(project);

    kafkaApplicationService.addAkhq(project);

    String pathToAkhqDockerComposeFile = MAIN_DOCKER + "/" + AKHQ_DOCKER_COMPOSE_FILE;
    assertFileExist(project, pathToAkhqDockerComposeFile);
    assertFileContent(project, pathToAkhqDockerComposeFile, "AKHQ_CONFIGURATION");
  }

  private List<String> kafkaClients() {
    return List.of("<dependency>", "<groupId>org.apache.kafka</groupId>", "<artifactId>kafka-clients</artifactId>", "</dependency>");
  }

  private List<String> testcontainers() {
    return List.of(
      "<dependency>",
      "<groupId>org.testcontainers</groupId>",
      "<artifactId>kafka</artifactId>",
      "<version>${testcontainers.version}</version>",
      "<scope>test</scope>",
      "</dependency>"
    );
  }
}
