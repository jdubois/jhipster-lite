package tech.jhipster.lite.module.domain;

import java.time.Instant;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public record JHipsterModuleApplied(JHipsterModuleSlug slug, JHipsterModuleProperties properties, Instant time) {
  public JHipsterModuleApplied {
    Assert.notNull("slug", slug);
    Assert.notNull("properties", properties);
    Assert.notNull("time", time);
  }
}
