package tech.jhipster.lite.project.infrastructure.secondary;

import tech.jhipster.lite.common.domain.ExcludeFromGeneratedCodeCoverage;
import tech.jhipster.lite.error.domain.GeneratorException;

@ExcludeFromGeneratedCodeCoverage
class ProjectFormattingException extends GeneratorException {

  public ProjectFormattingException(String message) {
    super(internalServerError(ProjectErrorKey.FORMATTING_ERROR).message(message));
  }

  public ProjectFormattingException(String message, Throwable cause) {
    super(internalServerError(ProjectErrorKey.FORMATTING_ERROR).message(message).cause(cause));
  }
}
