package tech.jhipster.lite.module.domain.javadependency;

import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;

class UnknownJavaVersionSlugException extends GeneratorException {

  public UnknownJavaVersionSlugException(VersionSlug slug) {
    super("Can't find property " + slug.propertyName() + ", forgot to add it in \"src/main/resources/generator/dependencies/pom.xml\"?");
  }
}
