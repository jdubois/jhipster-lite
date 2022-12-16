package tech.jhipster.lite.module.domain.properties;

import tech.jhipster.lite.error.domain.ErrorKey;

enum PropertiesErrorKey implements ErrorKey {
  INVALID_BASE_NAME("invalid-project-base-name"),
  INVALID_PROPERTY_TYPE("invalid-property-type"),
  UNKNOWN_PROPERTY("unknown-property");

  private final String key;

  PropertiesErrorKey(String key) {
    this.key = key;
  }

  @Override
  public String get() {
    return key;
  }
}
