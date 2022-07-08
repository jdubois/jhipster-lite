package tech.jhipster.lite.module.domain.replacement;

import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

interface FileReplacer {
  void apply(JHipsterProjectFolder folder);
}
