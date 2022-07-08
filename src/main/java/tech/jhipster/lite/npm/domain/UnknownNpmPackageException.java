package tech.jhipster.lite.npm.domain;

import tech.jhipster.lite.error.domain.GeneratorException;

public class UnknownNpmPackageException extends GeneratorException {

  public UnknownNpmPackageException(NpmPackageName packageName, String folder) {
    super(
      "Can't find " +
      packageName.get() +
      " version in src/main/resources/generator/dependencies/" +
      folder +
      "/package.json, forgot to add it?"
    );
  }
}
