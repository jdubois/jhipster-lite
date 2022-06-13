package tech.jhipster.lite.generator.module.domain.javaproperties;

import java.util.Collection;
import tech.jhipster.lite.error.domain.Assert;

public record SpringProperties(Collection<SpringProperty> properties) {
  public SpringProperties {
    Assert.field("properties", properties).notNull().noNullElement();
  }

  public Collection<SpringProperty> get() {
    return properties();
  }
}
