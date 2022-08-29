package tech.jhipster.lite.module.infrastructure.secondary;

import tech.jhipster.lite.error.domain.GeneratorException;

class UnknownFileToMoveException extends GeneratorException {

  public UnknownFileToMoveException(String filename) {
    super("Can't move " + filename + ", can't find it in project");
  }
}
