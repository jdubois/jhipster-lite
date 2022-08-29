package tech.jhipster.lite.git.infrastructure.secondary;

import tech.jhipster.lite.error.domain.GeneratorException;

class GitCommitException extends GeneratorException {

  public GitCommitException(String message, Throwable cause) {
    super(message, cause);
  }
}
