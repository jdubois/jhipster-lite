package tech.jhipster.lite.generator.client.tools.playwright.infrastructure.primary;

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
import tech.jhipster.lite.generator.client.tools.playwright.application.PlaywrightAssert;
import tech.jhipster.lite.generator.client.vue.core.application.VueApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
@AutoConfigureMockMvc
class PlaywrightResourceIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private VueApplicationService vueApplicationService;

  @Test
  void shouldInit() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(TestFileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    TestJHipsterModules.applyInit(project);

    JHipsterModuleProperties properties = projectDTO.toModuleProperties();
    TestJHipsterModules.applyer().module(vueApplicationService.buildVueModule(properties)).properties(properties).slug("vue").apply();

    mockMvc
      .perform(
        post("/api/clients/playwright").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    PlaywrightAssert.assertDependency(project);
    PlaywrightAssert.assertPlaywrightScripts(project);
    PlaywrightAssert.assertPlaywrightTestFiles(project);
    PlaywrightAssert.assertPlaywrightPageObjectFiles(project);
  }
}
