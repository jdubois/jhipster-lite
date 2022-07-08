package tech.jhipster.lite.module.infrastructure.primary;

import tech.jhipster.lite.error.domain.GeneratorException;

class DuplicatedSlugException extends GeneratorException {

  public DuplicatedSlugException() {
    super("Found a duplicated module slug, ensure that slugs are uniq");
  }
}
