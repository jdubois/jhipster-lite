package tech.jhipster.lite.module.domain.replacement;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;

public record JustLineBefore(ElementMatcher element) implements PositionalMatcher {
  public JustLineBefore {
    Assert.notNull("element", element);
  }

  @Override
  public String buildReplacement(String value) {
    Assert.notBlank("value", value);

    return value.concat(JHipsterModule.LINE_BREAK).concat(element().searchMatcher());
  }
}
