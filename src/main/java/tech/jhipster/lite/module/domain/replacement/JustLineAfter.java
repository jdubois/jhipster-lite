package tech.jhipster.lite.module.domain.replacement;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;

public record JustLineAfter(ElementMatcher element) implements PositionalMatcher {
  public JustLineAfter {
    Assert.notNull("element", element);
  }

  @Override
  public String buildReplacement(String value) {
    Assert.notBlank("value", value);

    return element().searchMatcher().concat(JHipsterModule.LINE_BREAK).concat(value);
  }
}
