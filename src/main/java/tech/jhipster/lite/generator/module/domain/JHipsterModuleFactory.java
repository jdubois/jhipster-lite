package tech.jhipster.lite.generator.module.domain;

import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;

@FunctionalInterface
public interface JHipsterModuleFactory {
  JHipsterModule create(JHipsterModuleProperties properties);
}
