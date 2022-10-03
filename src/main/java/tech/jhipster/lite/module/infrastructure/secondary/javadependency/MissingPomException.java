package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

class MissingPomException extends GeneratorException {

  public MissingPomException(JHipsterProjectFolder folder) {
    super("Can't find pom.xml in " + folder.get());
  }
}
