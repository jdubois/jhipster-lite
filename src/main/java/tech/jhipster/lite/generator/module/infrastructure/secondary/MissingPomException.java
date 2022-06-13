package tech.jhipster.lite.generator.module.infrastructure.secondary;

import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterProjectFolder;

class MissingPomException extends GeneratorException {

  public MissingPomException(JHipsterProjectFolder folder) {
    super("Can't find pom.xml in " + folder.get());
  }
}
