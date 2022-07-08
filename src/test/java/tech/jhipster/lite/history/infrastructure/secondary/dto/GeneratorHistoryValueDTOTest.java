package tech.jhipster.lite.history.infrastructure.secondary.dto;

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.history.domain.GeneratorHistoryValue;

@UnitTest
class GeneratorHistoryValueDTOTest {

  @Test
  void shouldConvertFromHistoryValue() {
    assertThat(GeneratorHistoryValueDTO.from(historyValue())).isEqualTo(historyDTOValue());
  }

  @Test
  void shouldConvertToHistoryValue() {
    assertThat(GeneratorHistoryValueDTO.to(historyDTOValue())).isEqualTo(historyValue());
  }

  private GeneratorHistoryValue historyValue() {
    return new GeneratorHistoryValue("service1664", Instant.ofEpochSecond(1000000));
  }

  private GeneratorHistoryValueDTO historyDTOValue() {
    return new GeneratorHistoryValueDTO("service1664", Instant.ofEpochSecond(1000000));
  }
}
