package tech.jhipster.lite.module.infrastructure.primary;

import tech.jhipster.lite.error.domain.GeneratorException;

public class InvalidProjectFolderException extends GeneratorException {

  public InvalidProjectFolderException() {
    super("Project folder is not valid");
  }
}
