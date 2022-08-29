package tech.jhipster.lite.module.domain.landscape;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModuleSlug;
import tech.jhipster.lite.module.domain.JHipsterSlug;

public record JHipsterModuleDependency(JHipsterModuleSlug module) implements JHipsterLandscapeDependency {
  public JHipsterModuleDependency {
    Assert.notNull("module", module);
  }

  @Override
  public JHipsterSlug slug() {
    return module();
  }

  @Override
  public JHipsterLandscapeElementType type() {
    return JHipsterLandscapeElementType.MODULE;
  }
}
