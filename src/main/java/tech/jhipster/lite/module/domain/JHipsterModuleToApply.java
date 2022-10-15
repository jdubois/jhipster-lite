package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public record JHipsterModuleToApply(JHipsterModuleSlug slug, JHipsterModuleProperties properties) {
  public JHipsterModuleToApply {
    Assert.notNull("slug", slug);
    Assert.notNull("properties", properties);
  }

  public boolean commitNeeded() {
    return properties().commitNeeded();
  }
}
