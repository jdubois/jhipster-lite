package tech.jhipster.lite.module.domain.replacement;

import tech.jhipster.lite.error.domain.GeneratorException;

public class MandatoryReplacementException extends GeneratorException {

  public MandatoryReplacementException(Throwable cause) {
    super("Error applying mandatory replacement", cause);
  }
}
