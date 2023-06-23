package tech.jhipster.lite.module.domain.file;

import java.util.Collection;
import tech.jhipster.lite.common.domain.JHipsterCollections;
import tech.jhipster.lite.error.domain.Assert;

public record JHipsterDestinations(Collection<JHipsterDestination> destinations) {
  public JHipsterDestinations(Collection<JHipsterDestination> destinations) {
    this.destinations = JHipsterCollections.immutable(destinations);
  }

  public boolean doesNotContain(JHipsterDestination destination) {
    Assert.notNull("destination", destination);

    return !destinations().contains(destination);
  }
}
