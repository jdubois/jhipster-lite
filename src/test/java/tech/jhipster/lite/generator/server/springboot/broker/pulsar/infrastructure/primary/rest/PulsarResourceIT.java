package tech.jhipster.lite.generator.server.springboot.broker.pulsar.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.server.springboot.broker.pulsar.domain.Pulsar.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
@AutoConfigureMockMvc
class PulsarResourceIT {

  @Autowired
  private SpringBootApplicationService springBootApplicationService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldInitPulsar() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(TestFileUtils.tmpDirForTest());
    if (projectDTO == null) {
      throw new GeneratorException("Error when reading file");
    }
    Project project = ProjectDTO.toProject(projectDTO);
    TestJHipsterModules.applyInit(project);
    TestJHipsterModules.applyMaven(project);
    springBootApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/brokers/pulsar")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String projectPath = projectDTO.getFolder();
    assertFileExist(projectPath, MAIN_DOCKER + "/" + PULSAR_DOCKER_COMPOSE_FILE);
    assertFileExist(projectPath, "src/main/java/tech/jhipster/chips/technical/infrastructure/config/pulsar/PulsarProperties.java");
    assertFileExist(projectPath, "src/main/java/tech/jhipster/chips/technical/infrastructure/config/pulsar/PulsarConfiguration.java");
    assertFileExist(projectPath, "src/test/java/tech/jhipster/chips/technical/infrastructure/config/pulsar/PulsarConfigurationIT.java");
    assertFileExist(projectPath, "src/test/java/tech/jhipster/chips/PulsarTestContainerExtension.java");
  }
}
