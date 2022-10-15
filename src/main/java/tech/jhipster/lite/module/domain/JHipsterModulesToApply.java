package tech.jhipster.lite.module.domain;

import java.util.Collection;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public record JHipsterModulesToApply(Collection<JHipsterModuleSlug> slugs, JHipsterModuleProperties properties) {
  public JHipsterModulesToApply {
    Assert.notEmpty("slugs", slugs);
    Assert.notNull("properties", properties);
  }
}
