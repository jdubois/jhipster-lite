package tech.jhipster.lite.history.infrastructure.secondary.dto;

import java.util.Collections;
import java.util.List;
import tech.jhipster.lite.history.domain.GeneratorHistoryData;
import tech.jhipster.lite.history.domain.GeneratorHistoryValue;

public record GeneratorHistoryDataDTO(List<GeneratorHistoryValueDTO> values) {
  public static GeneratorHistoryDataDTO from(List<GeneratorHistoryValue> values) {
    if (values == null) {
      return new GeneratorHistoryDataDTO(Collections.emptyList());
    }
    return new GeneratorHistoryDataDTO(values.stream().map(GeneratorHistoryValueDTO::from).distinct().toList());
  }

  public static GeneratorHistoryData to(List<GeneratorHistoryValueDTO> values) {
    if (values == null) {
      return new GeneratorHistoryData(Collections.emptyList());
    }
    return new GeneratorHistoryData(values.stream().map(GeneratorHistoryValueDTO::to).distinct().toList());
  }
}
