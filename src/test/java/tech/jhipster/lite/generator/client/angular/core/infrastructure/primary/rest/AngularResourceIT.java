package tech.jhipster.lite.generator.client.angular.core.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tech.jhipster.lite.TestUtils.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.generator.client.angular.core.application.AngularAssert;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
@AutoConfigureMockMvc
class AngularResourceIT {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldAddAngular() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(TestFileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    TestJHipsterModules.applyInit(project);

    mockMvc
      .perform(post("/api/clients/angular").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO)))
      .andExpect(status().isOk());

    AngularAssert.assertDevDependencies(project);
    AngularAssert.assertScripts(project);
    AngularAssert.assertConfigFiles(project);
  }
}
