package tech.jhipster.lite.project.infrastructure.secondary;

import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.error.domain.GeneratorException;

@Generated
class ProjectZippingException extends GeneratorException {

  public ProjectZippingException(String message) {
    super(message);
  }

  public ProjectZippingException(Throwable cause) {
    super("Error creating zip file", cause);
  }
}
