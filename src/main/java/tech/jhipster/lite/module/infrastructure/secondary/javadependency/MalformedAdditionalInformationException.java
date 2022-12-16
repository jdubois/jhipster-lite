package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import tech.jhipster.lite.error.domain.GeneratorException;

class MalformedAdditionalInformationException extends GeneratorException {

  public MalformedAdditionalInformationException(Throwable cause) {
    super(
      internalServerError(JavaDependencyErrorKey.MALFORMED_ADDITIONAL_INFORMATION)
        .message("Malfomed XML additional elements for plugin")
        .cause(cause)
    );
  }
}
