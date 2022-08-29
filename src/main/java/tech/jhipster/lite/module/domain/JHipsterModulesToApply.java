package tech.jhipster.lite.module.domain;

import java.util.Collection;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public record JHipsterModulesToApply(Collection<JHipsterModuleSlug> modules, JHipsterModuleProperties properties) {
  public JHipsterModulesToApply {
    Assert.notEmpty("modules", modules);
    Assert.notNull("properties", properties);
  }
}
