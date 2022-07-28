package tech.jhipster.lite.project.domain;

import tech.jhipster.lite.error.domain.GeneratorException;

public class UnknownProjectException extends GeneratorException {

  public UnknownProjectException() {
    super("A user tried to download an unknown project");
  }
}
