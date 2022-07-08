package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.error.domain.GeneratorException;

class InvalidJHipsterModuleSlugException extends GeneratorException {

  public InvalidJHipsterModuleSlugException(String slug) {
    super(
      "The module slug \"" +
      slug +
      "\" is invalid (blank, bad format, ...). Slug should be only lower case letters, numbers and hyphens (-)"
    );
  }
}
