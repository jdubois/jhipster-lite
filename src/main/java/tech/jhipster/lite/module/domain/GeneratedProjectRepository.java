package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

public interface GeneratedProjectRepository {
  JHipsterProjectFilesPaths list(JHipsterProjectFolder folder, JHipsterFileMatcher files);
}
