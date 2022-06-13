package tech.jhipster.lite.generator.history.application;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.generator.history.domain.HistoryProjectsFixture.*;

import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.history.domain.GeneratorHistoryValue;
import tech.jhipster.lite.generator.history.domain.HistoryProject;

@IntegrationTest
class GeneratorHistoryApplicationServiceIT {

  @Autowired
  private GeneratorHistoryApplicationService generatorHistoryApplicationService;

  @Test
  void shouldAddHistoryValue() throws IOException {
    // Given
    HistoryProject project = tmpProject();
    Instant instant = Instant.parse("2022-02-22T10:11:12.000Z");

    // When
    GeneratorHistoryValue generatorHistoryValue = new GeneratorHistoryValue("init-project", instant);
    generatorHistoryApplicationService.addHistoryValue(project, generatorHistoryValue);

    // Then
    String content = Files.readString(project.folder().filePath(".jhipster/history/history.json"));
    assertThat(content).isEqualTo(getExpectedHistoryFileContent());
    List<GeneratorHistoryValue> serviceIds = generatorHistoryApplicationService.getValues(project.folder());
    assertThat(serviceIds).extracting(GeneratorHistoryValue::serviceId).containsOnly("init-project");
  }

  private String getExpectedHistoryFileContent() {
    return """
        {
          "values" : [ {
            "serviceId" : "init-project",
            "timestamp" : "2022-02-22T10:11:12Z"
          } ]
        }""";
  }
}
