package tech.jhipster.lite.statistic.domain;

import tech.jhipster.lite.error.domain.Assert;

public record ProjectPath(String path) {
  public ProjectPath {
    Assert.notBlank("path", path);
  }

  public String get() {
    return path();
  }
}
