package tech.jhipster.lite.module.domain.resource;

import tech.jhipster.lite.error.domain.GeneratorException;

class DuplicatedSlugException extends GeneratorException {

  public DuplicatedSlugException() {
    super("Found a duplicated module slug, ensure that slugs are uniq");
  }
}
