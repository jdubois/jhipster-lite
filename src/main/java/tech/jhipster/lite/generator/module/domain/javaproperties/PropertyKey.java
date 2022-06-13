package tech.jhipster.lite.generator.module.domain.javaproperties;

import tech.jhipster.lite.error.domain.Assert;

public record PropertyKey(String key) {
  public PropertyKey {
    Assert.notBlank("key", key);
  }

  public String get() {
    return key();
  }
}
