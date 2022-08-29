package tech.jhipster.lite.module.domain.file;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;

public record JHipsterFileToMove(JHipsterProjectFilePath source, JHipsterDestination destination) {
  public JHipsterFileToMove {
    Assert.notNull("source", source);
    Assert.notNull("destination", destination);
  }
}
