package tech.jhipster.lite.generator.server.springboot.broker.kafka.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.server.springboot.broker.kafka.domain.Akhq.*;
import static tech.jhipster.lite.generator.server.springboot.broker.kafka.domain.Kafka.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.server.springboot.broker.kafka.application.KafkaApplicationService;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;

@IntegrationTest
@AutoConfigureMockMvc
class KafkaResourceIT {

  @Autowired
  private InitApplicationService initApplicationService;

  @Autowired
  private MavenApplicationService mavenApplicationService;

  @Autowired
  private SpringBootApplicationService springBootApplicationService;

  @Autowired
  private KafkaApplicationService kafkaApplicationService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldInitKafka() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    if (projectDTO == null) {
      throw new GeneratorException("Error when reading file");
    }
    Project project = ProjectDTO.toProject(projectDTO);
    initApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/brokers/kafka")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String projectPath = projectDTO.getFolder();
    assertFileExist(projectPath, MAIN_DOCKER + "/" + KAFKA_DOCKER_COMPOSE_FILE);
    assertFileExist(projectPath, "src/main/java/tech/jhipster/chips/technical/infrastructure/config/kafka/KafkaProperties.java");
    assertFileExist(projectPath, "src/test/java/tech/jhipster/chips/technical/infrastructure/config/kafka/KafkaPropertiesTest.java");
    assertFileExist(projectPath, "src/main/java/tech/jhipster/chips/technical/infrastructure/config/kafka/KafkaConfiguration.java");
    assertFileExist(projectPath, "src/test/java/tech/jhipster/chips/KafkaTestContainerExtension.java");
  }

  @Test
  void shouldAddProducerConsumer() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    if (projectDTO == null) {
      throw new GeneratorException("Error when reading file");
    }
    Project project = ProjectDTO.toProject(projectDTO);
    initApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);
    kafkaApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/brokers/kafka/dummy-producer-consumer")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String projectPath = projectDTO.getFolder();
    assertFileExist(projectPath, "src/main/java/tech/jhipster/chips/dummy/infrastructure/secondary/kafka/producer/DummyProducer.java");
    assertFileExist(projectPath, "src/test/java/tech/jhipster/chips/dummy/infrastructure/secondary/kafka/producer/DummyProducerTest.java");
    assertFileExist(projectPath, "src/main/java/tech/jhipster/chips/technical/infrastructure/config/kafka/KafkaConfiguration.java");

    assertFileExist(projectPath, "src/main/java/tech/jhipster/chips/dummy/infrastructure/primary/kafka/consumer/AbstractConsumer.java");
    assertFileExist(projectPath, "src/main/java/tech/jhipster/chips/dummy/infrastructure/primary/kafka/consumer/DummyConsumer.java");
    assertFileExist(projectPath, "src/test/java/tech/jhipster/chips/dummy/infrastructure/primary/kafka/consumer/DummyConsumerIT.java");
    assertFileExist(projectPath, "src/test/java/tech/jhipster/chips/dummy/infrastructure/primary/kafka/consumer/DummyConsumerTest.java");
  }

  @Test
  void shouldAkhq() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    initApplicationService.init(project);
    mavenApplicationService.init(project);
    springBootApplicationService.init(project);
    kafkaApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/brokers/kafka/akhq")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String projectPath = projectDTO.getFolder();
    assertFileExist(projectPath, MAIN_DOCKER + "/" + AKHQ_DOCKER_COMPOSE_FILE);
  }
}
