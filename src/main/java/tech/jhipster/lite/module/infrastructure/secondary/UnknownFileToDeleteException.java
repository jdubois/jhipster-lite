package tech.jhipster.lite.module.infrastructure.secondary;

import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;

class UnknownFileToDeleteException extends GeneratorException {

  public UnknownFileToDeleteException(JHipsterProjectFilePath file) {
    super("File to delete " + file.get() + ", can't be found");
  }
}
