package tech.jhipster.lite.generator.packagemanager.npm.infrastructure.primary.rest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tech.jhipster.lite.TestUtils.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmRepository;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
@AutoConfigureMockMvc
class NpmResourceIT {

  @Autowired
  private MockMvc mockMvc;

  @SpyBean
  private NpmRepository npmRepository;

  @Test
  void shouldInstall() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(TestFileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    TestJHipsterModules.applyInit(project);

    mockMvc
      .perform(
        post("/api/package-managers/npm/install").contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertFileExist(project, "node_modules");
  }

  @Test
  void shouldPrettify() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(TestFileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    TestJHipsterModules.applyInit(project);

    mockMvc
      .perform(
        post("/api/package-managers/npm/prettier-format")
          .contentType(MediaType.APPLICATION_JSON)
          .content(convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    verify(npmRepository).npmPrettierFormat(any(Project.class));
  }
}
