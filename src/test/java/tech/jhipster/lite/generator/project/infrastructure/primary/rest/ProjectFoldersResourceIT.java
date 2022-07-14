package tech.jhipster.lite.generator.project.infrastructure.primary.rest;

import static org.hamcrest.Matchers.matchesRegex;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;

@IntegrationTest
@AutoConfigureMockMvc
class ProjectFoldersResourceIT {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldGetProject() throws Exception {
    mockMvc.perform(get("/api/project-folders")).andExpect(content().string(matchesRegex("^.*.{36}$")));
  }
}
