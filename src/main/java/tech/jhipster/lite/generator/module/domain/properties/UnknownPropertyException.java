package tech.jhipster.lite.generator.module.domain.properties;

import tech.jhipster.lite.error.domain.GeneratorException;

class UnknownPropertyException extends GeneratorException {

  public UnknownPropertyException(String key) {
    super("Unknown property " + key);
  }
}
