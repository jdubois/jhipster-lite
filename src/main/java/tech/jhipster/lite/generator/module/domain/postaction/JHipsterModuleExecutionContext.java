package tech.jhipster.lite.generator.module.domain.postaction;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterProjectFolder;

public record JHipsterModuleExecutionContext(JHipsterProjectFolder projectFolder) {
  public JHipsterModuleExecutionContext {
    Assert.notNull("projectFolder", projectFolder);
  }
}
