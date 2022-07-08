package tech.jhipster.lite.history.infrastructure.secondary.dto;

import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("java:S110")
public class DefaultInstantDeserializer extends InstantDeserializer<Instant> {

  public DefaultInstantDeserializer() {
    super(
      Instant.class,
      DateTimeFormatter.ISO_INSTANT,
      Instant::from,
      a -> Instant.ofEpochMilli(a.value),
      a -> Instant.ofEpochSecond(a.integer, a.fraction),
      null,
      true // yes, replace zero offset with Z
    );
  }
}
