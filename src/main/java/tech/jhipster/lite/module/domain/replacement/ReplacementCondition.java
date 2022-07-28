package tech.jhipster.lite.module.domain.replacement;

public interface ReplacementCondition {
  boolean needReplacement(String contentBeforeReplacement, String replacement);

  static ReplacementCondition always() {
    return (contentBeforeReplacement, replacement) -> true;
  }

  static ReplacementCondition notContainingReplacement() {
    return (contentBeforeReplacement, replacement) -> !contentBeforeReplacement.contains(replacement);
  }
}
