package tech.jhipster.lite.module.domain.properties;

import tech.jhipster.lite.error.domain.GeneratorException;

class InvalidProjectBaseNameException extends GeneratorException {

  public InvalidProjectBaseNameException() {
    super("Project names can't have special characters, only letters (no accents) and numbers allowed");
  }
}
