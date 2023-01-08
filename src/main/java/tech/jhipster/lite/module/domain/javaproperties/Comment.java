package tech.jhipster.lite.module.domain.javaproperties;

import tech.jhipster.lite.error.domain.Assert;

public record Comment(String value) {
  public Comment {
    Assert.notBlank("value", value);
  }

  public String get() {
    return value();
  }
}
