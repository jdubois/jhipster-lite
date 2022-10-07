package tech.jhipster.lite.module.domain.resource;

import java.util.Optional;
import tech.jhipster.lite.module.domain.JHipsterFeatureSlug;

@FunctionalInterface
public interface JHipsterFeatureSlugFactory {
  String get();

  default Optional<JHipsterFeatureSlug> build() {
    return JHipsterFeatureSlug.of(get());
  }
}
