package tech.jhipster.lite.module.domain.properties;

import java.util.stream.Stream;
import tech.jhipster.lite.shared.error.domain.Assert;

public enum SpringConfigurationFormat {
  YAML("yaml"),
  PROPERTIES("properties");

  private final String format;

  SpringConfigurationFormat(String format) {
    Assert.notNull(format, "format");

    this.format = format;
  }

  public static SpringConfigurationFormat from(String input) {
    return Stream.of(values()).filter(configFormat -> configFormat.format.equals(input)).findFirst().orElse(null);
  }
}
