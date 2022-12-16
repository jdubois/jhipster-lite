package tech.jhipster.lite.error.infrastructure.primary;

import tech.jhipster.lite.error.domain.GeneratorException;

public final class GeneratorExceptionFactory {

  private GeneratorExceptionFactory() {}

  public static final GeneratorException buildEmptyException() {
    return GeneratorException.builder(null).build();
  }
}
