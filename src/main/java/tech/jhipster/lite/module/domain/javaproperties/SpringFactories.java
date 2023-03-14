package tech.jhipster.lite.module.domain.javaproperties;

import java.util.Collection;
import tech.jhipster.lite.error.domain.Assert;

public record SpringFactories(Collection<SpringFactory> factories) {
  public SpringFactories {
    Assert.field("factories", factories).notNull().noNullElement();
  }

  public Collection<SpringFactory> get() {
    return factories();
  }
}
