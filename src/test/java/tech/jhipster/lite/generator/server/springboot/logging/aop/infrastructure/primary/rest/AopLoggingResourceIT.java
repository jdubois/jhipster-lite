package tech.jhipster.lite.generator.server.springboot.logging.aop.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
@AutoConfigureMockMvc
class AopLoggingResourceIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInit() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(TestFileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    TestJHipsterModules.applyInit(project);
    TestJHipsterModules.applyMaven(project);
    springBootApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/log-tools/aop")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertFileContent(
      project,
      POM_XML,
      List.of("<groupId>org.springframework.boot</groupId>", "<artifactId>spring-boot-starter-aop</artifactId>")
    );
    assertFileExist(project, getPath(MAIN_JAVA, "tech/jhipster/chips/technical/infrastructure/secondary/aop/logging/LoggingAspect.java"));
    assertFileExist(
      project,
      getPath(MAIN_JAVA, "tech/jhipster/chips/technical/infrastructure/secondary/aop/logging/LoggingAspectConfiguration.java")
    );
    assertFileExist(
      project,
      getPath(TEST_JAVA, "tech/jhipster/chips/technical/infrastructure/secondary/aop/logging/LoggingAspectTest.java")
    );

    assertFileContent(project, getPath(MAIN_RESOURCES, "config/application.properties"), "application.aop.logging=false");
    assertFileContent(project, getPath(MAIN_RESOURCES, "config/application-local.properties"), "application.aop.logging=true");
    assertFileContent(project, getPath(TEST_RESOURCES, "config/application.properties"), "application.aop.logging=true");
  }
}
