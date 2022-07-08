package tech.jhipster.lite.module.domain.replacement;

import java.util.function.BiFunction;
import tech.jhipster.lite.error.domain.Assert;

public record TextMatcher(String text) implements ElementMatcher {
  public TextMatcher {
    Assert.notBlank("text", text);
  }

  @Override
  public boolean notMatchIn(String content) {
    return !content.contains(text());
  }

  @Override
  public BiFunction<String, String, String> replacer() {
    return (content, replacement) -> content.replace(text(), replacement);
  }
  @Override
  public String searchMatcher() {
    return text();
  }
}
