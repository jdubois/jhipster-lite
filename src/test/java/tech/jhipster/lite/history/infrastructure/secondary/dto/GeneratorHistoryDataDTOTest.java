package tech.jhipster.lite.history.infrastructure.secondary.dto;

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.history.domain.GeneratorHistoryValue;

@UnitTest
class GeneratorHistoryDataDTOTest {

  @Test
  void shouldHaveEmptyListFromNullDomain() {
    assertThat(GeneratorHistoryDataDTO.from(null).values()).isEmpty();
  }

  @Test
  void shouldConvertFromHistoryValue() {
    assertThat(GeneratorHistoryDataDTO.from(historyValues()).values()).isEqualTo(historyDTOValues());
  }

  @Test
  void shouldHaveEmptyListToNullDomain() {
    assertThat(GeneratorHistoryDataDTO.to(null).values()).isEmpty();
  }

  @Test
  void shouldConvertToHistoryValue() {
    assertThat(GeneratorHistoryDataDTO.to(historyDTOValues()).values()).isEqualTo(historyValues());
  }

  private List<GeneratorHistoryValue> historyValues() {
    return List.of(
      new GeneratorHistoryValue("service51", null),
      new GeneratorHistoryValue("service1664", Instant.ofEpochSecond(1000000)),
      new GeneratorHistoryValue("service33", Instant.ofEpochSecond(5000000))
    );
  }

  private List<GeneratorHistoryValueDTO> historyDTOValues() {
    return List.of(
      new GeneratorHistoryValueDTO("service51", null),
      new GeneratorHistoryValueDTO("service1664", Instant.ofEpochSecond(1000000)),
      new GeneratorHistoryValueDTO("service33", Instant.ofEpochSecond(5000000))
    );
  }
}
