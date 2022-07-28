package tech.jhipster.lite.project.infrastructure.secondary;

import tech.jhipster.lite.error.domain.GeneratorException;

class ProjectZippingException extends GeneratorException {

  public ProjectZippingException(Throwable cause) {
    super("Error creating zip file", cause);
  }
}
