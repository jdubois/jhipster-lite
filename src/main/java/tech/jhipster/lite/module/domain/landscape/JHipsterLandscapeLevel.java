package tech.jhipster.lite.module.domain.landscape;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterSlug;

public record JHipsterLandscapeLevel(Collection<JHipsterLandscapeElement> elements) {
  public JHipsterLandscapeLevel(Collection<JHipsterLandscapeElement> elements) {
    Assert.notEmpty("elements", elements);

    this.elements = Collections.unmodifiableCollection(elements);
  }

  public Stream<JHipsterSlug> slugs() {
    return elements.stream().flatMap(JHipsterLandscapeElement::slugs);
  }
}
