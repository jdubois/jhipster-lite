package tech.jhipster.lite.generator.module.domain;

import java.time.Instant;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;

public record JHipsterModuleApplied(JHipsterModuleProperties properties, JHipsterModuleSlug slug, Instant time) {
  public JHipsterModuleApplied {
    Assert.notNull("properties", properties);
    Assert.notNull("slug", slug);
    Assert.notNull("time", time);
  }
}
