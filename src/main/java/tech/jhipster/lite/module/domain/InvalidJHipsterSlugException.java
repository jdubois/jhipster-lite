package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.error.domain.GeneratorException;

class InvalidJHipsterSlugException extends GeneratorException {

  public InvalidJHipsterSlugException(String slug) {
    super("The slug \"" + slug + "\" is invalid (blank, bad format, ...). Slug should be only lower case letters, numbers and hyphens (-)");
  }
}
