package tech.jhipster.lite.module.domain.replacement;

import tech.jhipster.lite.error.domain.Assert;

public record JustAfter(ElementMatcher element) implements PositionalMatcher {
  public JustAfter {
    Assert.notNull("element", element);
  }
  @Override
  public String buildReplacement(String value) {
    Assert.notBlank("value", value);
    return element().searchMatcher().concat(value);
  }
}
