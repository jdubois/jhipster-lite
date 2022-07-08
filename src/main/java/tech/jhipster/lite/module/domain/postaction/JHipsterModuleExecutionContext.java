package tech.jhipster.lite.module.domain.postaction;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

public record JHipsterModuleExecutionContext(JHipsterProjectFolder projectFolder) {
  public JHipsterModuleExecutionContext {
    Assert.notNull("projectFolder", projectFolder);
  }
}
