package tech.jhipster.lite.module.domain.replacement;

import java.util.function.BiFunction;
import tech.jhipster.lite.error.domain.Assert;

public record TextReplacer(String text) implements ElementReplacer {
  public TextReplacer {
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
