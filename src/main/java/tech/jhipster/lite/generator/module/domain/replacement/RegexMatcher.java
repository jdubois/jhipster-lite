package tech.jhipster.lite.generator.module.domain.replacement;

import java.util.function.BiFunction;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;

public record RegexMatcher(Pattern pattern) implements ElementMatcher {
  public RegexMatcher(String regex) {
    this(buildPattern(regex));
  }

  private static Pattern buildPattern(String regex) {
    Assert.notBlank("regex", regex);

    try {
      return Pattern.compile(regex);
    } catch (PatternSyntaxException e) {
      throw new GeneratorException("Can't compile regex " + regex + ": " + e.getMessage(), e);
    }
  }

  public RegexMatcher {
    Assert.notNull("pattern", pattern);
  }

  @Override
  public boolean notMatchIn(String content) {
    return !pattern().matcher(content).find();
  }

  @Override
  public BiFunction<String, String, String> replacer() {
    return (content, replacement) -> pattern().matcher(content).replaceAll(replacement);
  }
}
