package tech.jhipster.lite.technical.infrastructure.primary;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;

@IntegrationTest
@AutoConfigureMockMvc
@SpringBootTest(properties = { "application.forced-project-folder=/forced-tmp" })
class ForcedProjectFolderPrefixIT {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldHandleProjectWithGoodProjectFolder() throws Exception {
    mockMvc
      .perform(post("/api/projects-test").contentType(MediaType.APPLICATION_JSON).content("{\"folder\": \"/forced-tmp/my-project\"}"))
      .andExpect(status().isOk());
  }

  @Test
  void shouldNotHandleProjectWithWrongProjectFolderPrefix() throws Exception {
    mockMvc
      .perform(post("/api/projects-test").contentType(MediaType.APPLICATION_JSON).content("{\"folder\": \"/home/my-project\"}"))
      .andExpect(status().isBadRequest());
  }

  @Test
  void shouldNotHandleProjectWithDotDotProjectFolderPrefix() throws Exception {
    mockMvc
      .perform(post("/api/projects-test").contentType(MediaType.APPLICATION_JSON).content("{\"folder\": \"/forced-tmp/../my-project\"}"))
      .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGetProject() throws Exception {
    mockMvc.perform(get("/api/project-folders")).andExpect(content().string(containsString("forced-tmp")));
  }
}
