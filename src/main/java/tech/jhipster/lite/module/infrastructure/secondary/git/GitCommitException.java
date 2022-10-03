package tech.jhipster.lite.module.infrastructure.secondary.git;

import tech.jhipster.lite.error.domain.GeneratorException;

class GitCommitException extends GeneratorException {

  public GitCommitException(String message, Throwable cause) {
    super(message, cause);
  }
}
