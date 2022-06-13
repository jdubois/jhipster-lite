package tech.jhipster.lite.generator.module.infrastructure.primary;

import tech.jhipster.lite.error.domain.Assert;

public record JHipsterModuleApiDoc(String tag, String operation) {
  public JHipsterModuleApiDoc {
    Assert.field("tag", tag).notBlank().maxLength(50);
    Assert.notBlank("operation", operation);
  }
}
