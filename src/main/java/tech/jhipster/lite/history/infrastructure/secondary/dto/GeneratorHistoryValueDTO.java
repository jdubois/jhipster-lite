package tech.jhipster.lite.history.infrastructure.secondary.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import java.time.Instant;
import tech.jhipster.lite.history.domain.GeneratorHistoryValue;

public record GeneratorHistoryValueDTO(
  String serviceId,

  @JsonSerialize(using = InstantSerializer.class) @JsonDeserialize(using = DefaultInstantDeserializer.class) Instant timestamp
) {
  public static GeneratorHistoryValueDTO from(GeneratorHistoryValue value) {
    return new GeneratorHistoryValueDTO(value.serviceId(), value.timestamp());
  }

  public static GeneratorHistoryValue to(GeneratorHistoryValueDTO value) {
    return new GeneratorHistoryValue(value.serviceId, value.timestamp);
  }
}
