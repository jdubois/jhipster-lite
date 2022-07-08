package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@FunctionalInterface
public interface JHipsterModuleFactory {
  JHipsterModule create(JHipsterModuleProperties properties);
}
