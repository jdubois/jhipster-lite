package tech.jhipster.lite.module.domain.replacement;

public interface ContentReplacer {
  String file();

  String apply(String content);

  void handleError(Throwable e);
}
