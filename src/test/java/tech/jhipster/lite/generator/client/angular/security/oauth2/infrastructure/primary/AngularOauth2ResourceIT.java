package tech.jhipster.lite.generator.client.angular.security.oauth2.infrastructure.primary;

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
import tech.jhipster.lite.generator.client.angular.security.oauth2.application.AngularOauth2Assert;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
@AutoConfigureMockMvc
class AngularOauth2ResourceIT {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldAddOauth2() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(TestFileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    TestJHipsterModules.applyInit(project);

    mockMvc
      .perform(post("/api/clients/angular").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO)))
      .andExpect(status().isOk());
    mockMvc
      .perform(
        post("/api/clients/angular/oauth2").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    AngularOauth2Assert.assertDependencies(project);
    AngularOauth2Assert.assertAddedFiles(project);
    AngularOauth2Assert.assertUpdatedFiles(project);
  }
}
