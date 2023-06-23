package tech.jhipster.lite.module.domain;

@FunctionalInterface
public interface JHipsterFileMatcher {
  boolean match(JHipsterProjectFilePath path);
}
