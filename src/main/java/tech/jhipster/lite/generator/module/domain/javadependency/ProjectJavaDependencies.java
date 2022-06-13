package tech.jhipster.lite.generator.module.domain.javadependency;

import java.util.Optional;

public record ProjectJavaDependencies(JavaDependenciesVersions versions, JavaDependencies dependencies) {
  public static final ProjectJavaDependencies EMPTY = new ProjectJavaDependencies(null, null);

  public ProjectJavaDependencies(JavaDependenciesVersions versions, JavaDependencies dependencies) {
    this.versions = buildVersions(versions);
    this.dependencies = buildDependencies(dependencies);
  }

  private JavaDependenciesVersions buildVersions(JavaDependenciesVersions versions) {
    if (versions == null) {
      return JavaDependenciesVersions.EMPTY;
    }

    return versions;
  }

  private JavaDependencies buildDependencies(JavaDependencies dependencies) {
    if (dependencies == null) {
      return JavaDependencies.EMPTY;
    }

    return dependencies;
  }

  public Optional<JavaDependencyVersion> version(VersionSlug slug) {
    return versions().get(slug);
  }

  public Optional<JavaDependency> dependency(DependencyId id) {
    return dependencies().get(id);
  }
}
