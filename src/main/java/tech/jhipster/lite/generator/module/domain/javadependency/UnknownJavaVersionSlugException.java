package tech.jhipster.lite.generator.module.domain.javadependency;

import tech.jhipster.lite.error.domain.GeneratorException;

class UnknownJavaVersionSlugException extends GeneratorException {

  public UnknownJavaVersionSlugException(VersionSlug slug) {
    super("Can't find property " + slug.propertyName() + ", forgot to add it in \"src/main/resources/generator/dependencies/pom.xml\"?");
  }
}
