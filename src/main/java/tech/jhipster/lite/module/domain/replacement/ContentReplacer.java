package tech.jhipster.lite.module.domain.replacement;

import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;

public interface ContentReplacer {
  JHipsterProjectFilePath file();

  String apply(String content);

  void handleError(Throwable e);
}
