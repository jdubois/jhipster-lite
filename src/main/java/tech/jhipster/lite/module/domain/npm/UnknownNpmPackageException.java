package tech.jhipster.lite.module.domain.npm;

import tech.jhipster.lite.error.domain.GeneratorException;

public class UnknownNpmPackageException extends GeneratorException {

  public UnknownNpmPackageException(NpmPackageName packageName, NpmVersionSource source) {
    super("Can't find " + packageName.get() + " version in " + source + "/package.json, forgot to add it?");
  }
}
