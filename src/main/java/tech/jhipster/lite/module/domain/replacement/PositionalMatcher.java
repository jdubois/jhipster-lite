package tech.jhipster.lite.module.domain.replacement;

interface PositionalMatcher {
  ElementMatcher element();

  String buildReplacement(String value);
}
