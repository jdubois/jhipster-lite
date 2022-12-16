package tech.jhipster.lite.module.domain.replacement;

import tech.jhipster.lite.error.domain.GeneratorException;

public class MandatoryReplacementException extends GeneratorException {

  public MandatoryReplacementException(Throwable cause) {
    super(
      internalServerError(ReplacementErrorKey.MANDATORY_REPLACEMENT_ERROR).message("Error applying mandatory replacement").cause(cause)
    );
  }
}
