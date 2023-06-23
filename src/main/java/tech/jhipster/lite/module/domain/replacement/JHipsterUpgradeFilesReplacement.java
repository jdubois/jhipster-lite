package tech.jhipster.lite.module.domain.replacement;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterFileMatcher;

public record JHipsterUpgradeFilesReplacement(JHipsterFileMatcher files, ElementReplacer replacer, String replacement) {
  public JHipsterUpgradeFilesReplacement {
    Assert.notNull("files", files);
    Assert.notNull("replacer", replacer);
    Assert.notNull("replacement", replacement);
  }
}
