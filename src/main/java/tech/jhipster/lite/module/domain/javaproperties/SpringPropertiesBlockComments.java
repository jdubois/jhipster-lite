package tech.jhipster.lite.module.domain.javaproperties;

import java.util.Collection;
import java.util.List;
import tech.jhipster.lite.shared.error.domain.Assert;

public record SpringPropertiesBlockComments(Collection<SpringPropertiesBlockComment> propertiesBlockComments) {
  public static final SpringPropertiesBlockComments EMPTY = new SpringPropertiesBlockComments(List.of());

  public SpringPropertiesBlockComments {
    Assert.field("propertiesBlockComments", propertiesBlockComments).notNull().noNullElement();
  }

  public Collection<SpringPropertiesBlockComment> get() {
    return propertiesBlockComments();
  }
}
