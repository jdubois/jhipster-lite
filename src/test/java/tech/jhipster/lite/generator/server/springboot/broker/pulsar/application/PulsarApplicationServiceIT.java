package tech.jhipster.lite.generator.server.springboot.broker.pulsar.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.server.springboot.broker.pulsar.domain.Pulsar.*;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;

@IntegrationTest
class PulsarApplicationServiceIT {

  @Autowired
  private PulsarApplicationService pulsarApplicationService;

  @Autowired
  private InitApplicationService initApplicationService;

  @Autowired
  private MavenApplicationService mavenApplicationService;

  @Autowired
  private SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    pulsarApplicationService.init(project);
    assertFileContent(project, POM_XML, "<pulsar.version>");
    assertFileContent(project, POM_XML, "</pulsar.version>");
    assertFileContent(project, POM_XML, pulsarClient());

    String pathToPulsarDockerComposeFile = MAIN_DOCKER + "/" + PULSAR_DOCKER_COMPOSE_FILE;
    assertFileExist(project, pathToPulsarDockerComposeFile);
    assertFileContent(project, pathToPulsarDockerComposeFile, "PULSAR_MEM:");

    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "# Pulsar Configuration");
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES),
      "pulsar.client.service-url=pulsar://localhost:6650"
    );

    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "# Pulsar Configuration");
    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "pulsar.producer.topic-name=test-topic");

    assertFileContent(project, POM_XML, "<testcontainers.version>");
    assertFileContent(project, POM_XML, "</testcontainers.version>");
    assertFileContent(project, POM_XML, testcontainers());

    String packagePath = getPath("com/mycompany/myapp");
    String configPulsarPath = getPath(packagePath, "technical/infrastructure/config/pulsar");
    assertFileExist(project, getPath(MAIN_JAVA, configPulsarPath, "PulsarProperties.java"));
    assertFileContent(project, getPath(MAIN_JAVA, configPulsarPath, "PulsarProperties.java"), "public class PulsarProperties");

    assertFileExist(project, getPath(MAIN_JAVA, configPulsarPath, "PulsarConfiguration.java"));
    assertFileContent(project, getPath(MAIN_JAVA, configPulsarPath, "PulsarConfiguration.java"), "public class PulsarConfiguration");

    assertFileExist(project, getPath(TEST_JAVA, configPulsarPath, "PulsarConfigurationIT.java"));
    assertFileContent(project, getPath(TEST_JAVA, configPulsarPath, "PulsarConfigurationIT.java"), "class PulsarConfigurationIT");

    assertFileExist(project, getPath(TEST_JAVA, packagePath, "PulsarTestContainerExtension.java"));
    assertFileContent(
      project,
      getPath(TEST_JAVA, packagePath, "PulsarTestContainerExtension.java"),
      "public class PulsarTestContainerExtension"
    );
  }

  private List<String> pulsarClient() {
    return List.of(
      "<dependency>",
      "<groupId>org.apache.pulsar</groupId>",
      "<artifactId>pulsar-client</artifactId>",
      "<version>${pulsar.version}</version>",
      "</dependency>"
    );
  }

  private List<String> testcontainers() {
    return List.of(
      "<dependency>",
      "<groupId>org.testcontainers</groupId>",
      "<artifactId>pulsar</artifactId>",
      "<version>${testcontainers.version}</version>",
      "<scope>test</scope>",
      "</dependency>"
    );
  }
}
