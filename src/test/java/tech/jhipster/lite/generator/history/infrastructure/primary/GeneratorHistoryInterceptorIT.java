package tech.jhipster.lite.generator.history.infrastructure.primary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.TestUtils.convertObjectToJsonBytes;
import static tech.jhipster.lite.TestUtils.readFileToObject;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;

import java.time.Clock;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class GeneratorHistoryInterceptorIT {

  @Autowired
  MockMvc mockMvc;

  @Mock
  Clock clock;

  @Autowired
  GeneratorHistoryInterceptor generatorHistoryInterceptor;

  @Test
  void shouldAddInHistory() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());
    when(clock.instant()).thenReturn(Instant.parse("2022-01-22T14:01:54Z"));
    ReflectionTestUtils.setField(generatorHistoryInterceptor, "clock", clock);

    mockMvc
      .perform(post("/api/inits/full").contentType(MediaType.APPLICATION_JSON).content(convertObjectToJsonBytes(projectDTO)))
      .andExpect(status().isOk());
    // Then
    String content = FileUtils.read(getPath(projectDTO.getFolder(), ".jhipster/history", "history.json"));
    assertThat(content).isEqualTo(getExpectedHistoryFileContent());
  }

  @Test
  void shouldNotAddHistoryWhenException() throws Exception {
    ProjectDTO projectDTO = readFileToObject("json/chips.json", ProjectDTO.class).folder(FileUtils.tmpDirForTest());

    mockMvc
      .perform(
        post("/api/servers/spring-boot/databases/mysql")
          .contentType(MediaType.APPLICATION_JSON)
          .content(convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isBadRequest());

    // Then
    assertThat(FileUtils.exists(getPath(projectDTO.getFolder(), ".jhipster/history", "history.json"))).isFalse();
  }

  private String getExpectedHistoryFileContent() {
    return """
      {
        "values" : [ {
          "serviceId" : "init",
          "timestamp" : "2022-01-22T14:01:54Z"
        } ]
      }""";
  }
}
