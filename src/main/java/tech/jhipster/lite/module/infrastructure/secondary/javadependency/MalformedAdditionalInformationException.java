package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import tech.jhipster.lite.error.domain.GeneratorException;

class MalformedAdditionalInformationException extends GeneratorException {

  public MalformedAdditionalInformationException(Throwable cause) {
    super("Malfomed XML additional elements for plugin", cause);
  }
}
