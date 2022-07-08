package tech.jhipster.lite.history.infrastructure.primary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import tech.jhipster.lite.history.domain.GeneratorHistoryValue;

public record HistoryDTO(@Schema(name = "Service Ids applied to project", required = true) Collection<String> serviceIds) {
  public static HistoryDTO from(List<GeneratorHistoryValue> serviceIds) {
    if (serviceIds == null) {
      return new HistoryDTO(Collections.emptyList());
    }
    return new HistoryDTO(serviceIds.stream().map(GeneratorHistoryValue::serviceId).distinct().sorted().toList());
  }
}
