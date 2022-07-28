package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.application.FlywayAssert.*;

import java.time.Clock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
@AutoConfigureMockMvc
class FlywayResourceIT {

  @SpyBean
  private Clock clock;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    initClock(clock);
  }

  @Test
  void shouldAddUserAndAuthority() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class);
    if (projectDTO == null) {
      throw new GeneratorException("Error when reading file");
    }
    projectDTO.folder(TestFileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);

    TestJHipsterModules.applyInit(project);
    TestJHipsterModules.applyMaven(project);
    TestJHipsterModules.applySpringBootCore(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/database-migration-tools/flyway/user")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertUserAuthoritySqlFile(project);
  }
}
