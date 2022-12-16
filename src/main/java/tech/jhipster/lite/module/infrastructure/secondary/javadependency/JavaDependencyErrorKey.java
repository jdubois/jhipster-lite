package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import tech.jhipster.lite.error.domain.ErrorKey;

enum JavaDependencyErrorKey implements ErrorKey {
  MALFORMED_ADDITIONAL_INFORMATION("malformed-java-dependency-additional-information"),
  INVALID_POM("invalid-pom-file"),
  MISSING_POM("missing-pom-file");

  private final String key;

  JavaDependencyErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
