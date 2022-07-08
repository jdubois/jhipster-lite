package tech.jhipster.lite.npm.domain;

import tech.jhipster.lite.error.domain.Assert;

public record NpmPackageName(String packageName) {
  public NpmPackageName {
    Assert.notBlank("packageName", packageName);
  }

  public String get() {
    return packageName();
  }
}
