package tech.jhipster.lite.module.domain.replacement;

import java.util.function.BiFunction;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.shared.error.domain.Assert;

public record TextNeedleAfterReplacer(ReplacementCondition condition, String text) implements ElementReplacer {
  public TextNeedleAfterReplacer {
    Assert.notNull("condition", condition);
    Assert.notBlank("text", text);
  }

  @Override
  public boolean notMatchIn(String content) {
    Assert.notNull("content", content);

    return !content.contains(text());
  }

  @Override
  public BiFunction<String, String, String> replacement() {
    return (content, replacement) -> {
      String replacementBlock = JHipsterModule.LINE_BREAK + replacement;
      return content.replaceAll(this.text, this.text + replacementBlock);
    };
  }

  @Override
  public String searchMatcher() {
    return text();
  }
}
