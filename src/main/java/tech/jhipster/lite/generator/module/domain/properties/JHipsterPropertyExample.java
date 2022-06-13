package tech.jhipster.lite.generator.module.domain.properties;

import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import tech.jhipster.lite.error.domain.Assert;

public record JHipsterPropertyExample(String example) {
  public JHipsterPropertyExample {
    Assert.notBlank("example", example);
  }

  public static Optional<JHipsterPropertyExample> of(String example) {
    return Optional.ofNullable(example).filter(StringUtils::isNotBlank).map(JHipsterPropertyExample::new);
  }

  public String get() {
    return example();
  }
}
