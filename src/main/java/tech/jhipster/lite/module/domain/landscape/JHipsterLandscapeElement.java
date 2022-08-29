package tech.jhipster.lite.module.domain.landscape;

import java.util.Optional;
import java.util.stream.Stream;
import tech.jhipster.lite.module.domain.JHipsterSlug;

public sealed interface JHipsterLandscapeElement permits JHipsterLandscapeFeature, JHipsterLandscapeModule {
  JHipsterLandscapeElementType type();

  JHipsterSlug slug();

  Optional<JHipsterLandscapeDependencies> dependencies();

  Stream<JHipsterLandscapeModule> allModules();

  Stream<JHipsterSlug> slugs();
}
