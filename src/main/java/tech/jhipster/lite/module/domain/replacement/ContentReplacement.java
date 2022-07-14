package tech.jhipster.lite.module.domain.replacement;

public interface ContentReplacement {
  String file();

  String apply(String content);

  void handleError(Throwable e);
}
