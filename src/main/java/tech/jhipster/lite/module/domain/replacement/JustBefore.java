package tech.jhipster.lite.module.domain.replacement;

import tech.jhipster.lite.error.domain.Assert;

public record JustBefore(ElementMatcher element) implements PositionalMatcher {
  public JustBefore {
    Assert.notNull("element", element);
  }

  @Override
  public String buildReplacement(String value) {
    Assert.notBlank("value", value);
    return value.concat(element().searchMatcher());
  }
}
