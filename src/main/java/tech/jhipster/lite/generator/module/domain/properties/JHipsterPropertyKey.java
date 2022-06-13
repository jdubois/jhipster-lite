package tech.jhipster.lite.generator.module.domain.properties;

import tech.jhipster.lite.error.domain.Assert;

public record JHipsterPropertyKey(String key) {
  public JHipsterPropertyKey {
    Assert.notBlank("key", key);
  }

  public String get() {
    return key();
  }
}
