package tech.jhipster.lite.generator.module.domain;

import tech.jhipster.lite.error.domain.Assert;

record JHipsterModuleFile(JHipsterFileContent content, JHipsterDestination destination) {
  public JHipsterModuleFile {
    Assert.notNull("content", content);
    Assert.notNull("destination", destination);
  }
}
