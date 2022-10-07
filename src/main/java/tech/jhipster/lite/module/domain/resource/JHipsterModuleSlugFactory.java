package tech.jhipster.lite.module.domain.resource;

import tech.jhipster.lite.module.domain.JHipsterModuleSlug;

@FunctionalInterface
public interface JHipsterModuleSlugFactory {
  String get();

  default JHipsterModuleSlug build() {
    return new JHipsterModuleSlug(get());
  }
}
