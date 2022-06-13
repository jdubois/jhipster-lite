package tech.jhipster.lite.generator.module.infrastructure.primary;

import java.io.IOException;
import tech.jhipster.lite.error.domain.GeneratorException;

class InvalidModuleQueryException extends GeneratorException {

  private InvalidModuleQueryException(String message) {
    super(message);
  }

  private InvalidModuleQueryException(String message, Throwable cause) {
    super(message, cause);
  }

  static InvalidModuleQueryException unsupportedMethod() {
    return new InvalidModuleQueryException("Only POST supported for JHipster module with a project DTO");
  }

  static InvalidModuleQueryException unreadablePayload(IOException cause) {
    return new InvalidModuleQueryException("Can't read payload to apply module: " + cause.getMessage(), cause);
  }
}
