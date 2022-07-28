package tech.jhipster.lite.project.domain.download;

import tech.jhipster.lite.error.domain.GeneratorException;

class InvalidDownloadException extends GeneratorException {

  public InvalidDownloadException() {
    super("A user tried to download a project from a protected folder");
  }
}
