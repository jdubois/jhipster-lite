package tech.jhipster.lite.history.infrastructure.secondary.dto;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.JsonUtils;

@UnitTest
class DefaultInstanceDeserializerTest {

  @Test
  void shouldReadHistory() throws Exception {
    //Given
    ObjectMapper objectMapper = JsonUtils.getObjectMapper();

    // When
    List<GeneratorHistoryValueDTO> historyValues = objectMapper.readValue(getLongFileContent(), GeneratorHistoryDataDTO.class).values();

    // Then
    List<GeneratorHistoryValueDTO> expectedHistoryValues = new ArrayList<>();
    expectedHistoryValues.add(new GeneratorHistoryValueDTO("service1", Instant.parse("1970-05-23T21:21:18.900Z")));
    expectedHistoryValues.add(new GeneratorHistoryValueDTO("service2", Instant.parse("1970-09-29T11:46:29Z")));
    expectedHistoryValues.add(new GeneratorHistoryValueDTO("service3", Instant.parse("2022-01-24T10:11:12Z")));
    assertThat(historyValues).usingRecursiveComparison().isEqualTo(expectedHistoryValues);
  }

  @Test
  void shouldReadHistoryWithNanoRead() throws Exception {
    //Given
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

    // When
    List<GeneratorHistoryValueDTO> historyValues = objectMapper.readValue(getLongFileContent(), GeneratorHistoryDataDTO.class).values();

    // Then
    List<GeneratorHistoryValueDTO> expectedHistoryValues = new ArrayList<>();
    expectedHistoryValues.add(new GeneratorHistoryValueDTO("service1", Instant.parse("1970-05-23T21:21:18.900Z")));
    expectedHistoryValues.add(new GeneratorHistoryValueDTO("service2", Instant.parse("1970-01-01T06:30:56.789Z")));
    expectedHistoryValues.add(new GeneratorHistoryValueDTO("service3", Instant.parse("2022-01-24T10:11:12Z")));
    assertThat(historyValues).usingRecursiveComparison().isEqualTo(expectedHistoryValues);
  }

  private String getLongFileContent() {
    return """
      {
        "values": [
          { "serviceId": "service1", "timestamp": 12345678.9},
          { "serviceId": "service2", "timestamp": 23456789},
          { "serviceId": "service3", "timestamp": "2022-01-24T10:11:12Z"}
        ]
      }
      """;
  }
}
