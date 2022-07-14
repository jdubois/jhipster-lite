package tech.jhipster.lite.module.domain.replacement;

import java.util.function.BiFunction;

public interface ElementReplacer {
  boolean notMatchIn(String content);

  BiFunction<String, String, String> replacer();

  String searchMatcher();
}
