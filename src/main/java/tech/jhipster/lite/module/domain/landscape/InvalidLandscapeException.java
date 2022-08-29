package tech.jhipster.lite.module.domain.landscape;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.module.domain.JHipsterSlug;

class InvalidLandscapeException extends GeneratorException {

  private InvalidLandscapeException(String message) {
    super(message);
  }

  static InvalidLandscapeException duplicatedSlug(String slug) {
    return new InvalidLandscapeException("Can't share a slug between a feature and a module, slug \"" + slug + "\" is duplicated");
  }

  public static InvalidLandscapeException unknownDepdencency(Set<JHipsterSlug> knownSlugs, List<JHipsterSlug> remainingElements) {
    return new InvalidLandscapeException(
      "Can't build landscape this happens if you have an unknown dependency or circular dependencies. Known elements: " +
      displayableSlugs(knownSlugs) +
      " and trying to find element with all known dependencies in: " +
      displayableSlugs(remainingElements)
    );
  }

  private static String displayableSlugs(Collection<JHipsterSlug> slugs) {
    return slugs.stream().map(JHipsterSlug::get).collect(Collectors.joining(", "));
  }

  public static InvalidLandscapeException missingRootElement() {
    return new InvalidLandscapeException("Can't build landscape, can't find any root element");
  }
}
