package tech.jhipster.lite.module.domain.landscape;

import tech.jhipster.lite.module.domain.JHipsterSlug;

public sealed interface JHipsterLandscapeDependency permits JHipsterFeatureDependency, JHipsterModuleDependency {
  JHipsterSlug slug();

  JHipsterLandscapeElementType type();
}
