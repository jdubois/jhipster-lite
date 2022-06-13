package tech.jhipster.lite.generator.module.domain.replacement;

import java.util.function.BiFunction;

interface ElementMatcher {
  boolean notMatchIn(String content);

  BiFunction<String, String, String> replacer();
}
