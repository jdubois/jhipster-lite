package tech.jhipster.lite.generator.ci.github.actions.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.ci.github.actions.application.GitHubActionsAssertFiles.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
@AutoConfigureMockMvc
class GitHubActionsResourceIT {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldAddGitHubActionsForMaven() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(TestFileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);

    TestJHipsterModules.applyGradle(project);

    mockMvc
      .perform(
        post("/api/developer-tools/github-actions").contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertFilesGitHubActions(project);
  }

  @Test
  void shouldAddGitHubActionsForGradle() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(TestFileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);

    TestJHipsterModules.applyGradle(project);

    mockMvc
      .perform(
        post("/api/developer-tools/github-actions").contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertFilesGitHubActions(project);
  }
}
