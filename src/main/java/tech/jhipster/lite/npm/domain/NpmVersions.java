package tech.jhipster.lite.npm.domain;

public interface NpmVersions {
  /**
   * Get the npm package version from the given source
   *
   * @param packageName
   *          name of the package to get the version for
   * @param source
   *          source folder for this version
   * @return The version
   * @throws UnknownNpmPackageException
   *           is the package can't be found in source
   */
  NpmVersion get(NpmPackageName packageName, NpmVersionSource source);

  /**
   * Get the npm package version from the given source
   *
   * @param packageName
   *          name of the package to get the version for
   * @param source
   *          source folder for this version
   * @return The version
   * @throws UnknownNpmPackageException
   *           is the package can't be found in source
   */
  default NpmVersion get(String packageName, NpmVersionSource source) {
    return get(new NpmPackageName(packageName), source);
  }
}
