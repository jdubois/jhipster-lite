package tech.jhipster.lite.module.domain.javaproperties;

import java.util.Collection;
import java.util.List;
import tech.jhipster.lite.shared.error.domain.Assert;

public record SpringProperties(Collection<SpringProperty> properties) {
  public static final SpringProperties EMPTY = new SpringProperties(List.of());

  public SpringProperties {
    Assert.field("properties", properties).notNull().noNullElement();
  }

  public Collection<SpringProperty> get() {
    return properties();
  }
}
