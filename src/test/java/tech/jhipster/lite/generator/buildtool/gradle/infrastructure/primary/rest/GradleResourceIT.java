package tech.jhipster.lite.generator.buildtool.gradle.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.readFileToObject;
import static tech.jhipster.lite.generator.buildtool.gradle.application.GradleAssertFiles.assertFilesBuildGradleKts;
import static tech.jhipster.lite.generator.buildtool.gradle.application.GradleAssertFiles.assertFilesGradle;
import static tech.jhipster.lite.generator.buildtool.gradle.application.GradleAssertFiles.assertFilesGradleWrapper;
import static tech.jhipster.lite.generator.project.domain.Constants.BUILD_GRADLE_KTS;
import static tech.jhipster.lite.generator.project.domain.Constants.SETTINGS_GRADLE_KTS;

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

@IntegrationTest
@AutoConfigureMockMvc
class GradleResourceIT {

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldInit() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(TestFileUtils.tmpDirForTest());

    mockMvc
      .perform(
        post("/api/build-tools/gradle").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    Project project = ProjectDTO.toProject(projectDTO);
    assertFilesGradle(project);

    assertFileContent(project, SETTINGS_GRADLE_KTS, "rootProject.name = \"chips\"");
    assertFileContent(project, BUILD_GRADLE_KTS, "group = \"tech.jhipster.chips\"");
  }

  @Test
  void shouldAddBuildGradleKts() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(TestFileUtils.tmpDirForTest());

    mockMvc
      .perform(
        post("/api/build-tools/gradle/build-gradle")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    Project project = ProjectDTO.toProject(projectDTO);
    assertFilesBuildGradleKts(project);
    assertFileContent(project, SETTINGS_GRADLE_KTS, "rootProject.name = \"chips\"");
    assertFileContent(project, BUILD_GRADLE_KTS, "group = \"tech.jhipster.chips\"");
  }

  @Test
  void shouldAddGradleWrapper() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(TestFileUtils.tmpDirForTest());

    mockMvc
      .perform(
        post("/api/build-tools/gradle/wrapper")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    Project project = ProjectDTO.toProject(projectDTO);
    assertFilesGradleWrapper(project);
  }
}
