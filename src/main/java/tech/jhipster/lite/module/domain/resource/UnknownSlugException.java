package tech.jhipster.lite.module.domain.resource;

import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.module.domain.JHipsterModuleSlug;

class UnknownSlugException extends GeneratorException {

  public UnknownSlugException(JHipsterModuleSlug slug) {
    super("Module %s does not exist".formatted(slug.get()));
  }
}
