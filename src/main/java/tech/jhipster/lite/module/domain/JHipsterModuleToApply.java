package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public record JHipsterModuleToApply(JHipsterModuleSlug slug, JHipsterModule module) {
  public JHipsterModuleToApply {
    Assert.notNull("slug", slug);
    Assert.notNull("module", module);
  }

  public boolean commitNeeded() {
    return properties().commitNeeded();
  }

  public JHipsterModuleProperties properties() {
    return module.properties();
  }
}
