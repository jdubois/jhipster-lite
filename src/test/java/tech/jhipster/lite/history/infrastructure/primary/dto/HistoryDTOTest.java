package tech.jhipster.lite.history.infrastructure.primary.dto;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.history.domain.GeneratorHistoryValue;

@UnitTest
public class HistoryDTOTest {

  @Test
  void shouldHaveEmptyListFromNullDomain() {
    assertThat(HistoryDTO.from(null).serviceIds()).isEmpty();
  }

  @Test
  void shouldConvertFromHistory() {
    HistoryDTO history = defaultHistory();

    assertThat(history.serviceIds()).isEqualTo(List.of("service1664", "service33", "service51"));
  }

  @Test
  void shouldSerializeToJson() throws JsonProcessingException {
    assertThat(JsonMapper.builder().build().writeValueAsString(defaultHistory())).isEqualTo(defaultJson());
  }

  private HistoryDTO defaultHistory() {
    return HistoryDTO.from(values());
  }

  public static List<GeneratorHistoryValue> values() {
    return historyValues();
  }

  private static List<GeneratorHistoryValue> historyValues() {
    return List.of(
      new GeneratorHistoryValue("service51", null),
      new GeneratorHistoryValue("service1664", Instant.ofEpochSecond(1000000)),
      new GeneratorHistoryValue("service33", Instant.ofEpochSecond(5000000))
    );
  }

  public static String defaultJson() {
    return ("{\"serviceIds\":[\"service1664\",\"service33\",\"service51\"]}");
  }
}
