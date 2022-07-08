package tech.jhipster.lite.module.domain.javadependency;

import tech.jhipster.lite.error.domain.Assert;

public record Version(String version) {
  public Version {
    Assert.notBlank("version", version);
  }

  public String get() {
    return version();
  }
}
