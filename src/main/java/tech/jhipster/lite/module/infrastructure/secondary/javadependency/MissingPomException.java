package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

class MissingPomException extends GeneratorException {

  public MissingPomException(JHipsterProjectFolder folder) {
    super(badRequest(JavaDependencyErrorKey.MISSING_POM).message(buildMessage(folder)).addParameter("folder", folder.get()));
  }

  private static String buildMessage(JHipsterProjectFolder folder) {
    return new StringBuilder().append("Can't find pom.xml in ").append(folder.get()).toString();
  }
}
