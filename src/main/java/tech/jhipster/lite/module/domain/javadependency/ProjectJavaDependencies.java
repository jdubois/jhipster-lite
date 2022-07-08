package tech.jhipster.lite.module.domain.javadependency;

import java.util.Optional;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;

public class ProjectJavaDependencies {

  public static final ProjectJavaDependencies EMPTY = builder().versions(null).dependenciesManagements(null).dependencies(null);

  private final JavaDependenciesVersions versions;
  private final JavaDependencies dependenciesManagement;
  private final JavaDependencies dependencies;

  private ProjectJavaDependencies(ProjectJavaDependenciesBuilder builder) {
    versions = buildVersions(builder.versions);
    dependenciesManagement = buildDependencies(builder.dependenciesManagement);
    dependencies = buildDependencies(builder.dependencies);
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

  public static ProjectJavaDependenciesVersionsBuilder builder() {
    return new ProjectJavaDependenciesBuilder();
  }

  public Optional<JavaDependencyVersion> version(VersionSlug slug) {
    return versions.get(slug);
  }

  public Optional<JavaDependency> dependency(DependencyId id) {
    return dependencies.get(id);
  }

  public Optional<JavaDependency> dependencyManagement(DependencyId id) {
    return dependenciesManagement.get(id);
  }

  public JavaDependenciesVersions versions() {
    return versions;
  }

  public JavaDependencies dependenciesManagement() {
    return dependenciesManagement;
  }

  public JavaDependencies dependencies() {
    return dependencies;
  }

  public static class ProjectJavaDependenciesBuilder
    implements
      ProjectJavaDependenciesVersionsBuilder,
      ProjectJavaDependenciesDependenciesManagementBuilder,
      ProjectJavaDependenciesDependenciesBuilder {

    private JavaDependenciesVersions versions;
    private JavaDependencies dependenciesManagement;
    private JavaDependencies dependencies;

    @Override
    public ProjectJavaDependenciesDependenciesManagementBuilder versions(JavaDependenciesVersions versions) {
      this.versions = versions;

      return this;
    }

    @Override
    public ProjectJavaDependenciesDependenciesBuilder dependenciesManagements(JavaDependencies dependenciesManagement) {
      this.dependenciesManagement = dependenciesManagement;

      return this;
    }

    @Override
    public ProjectJavaDependencies dependencies(JavaDependencies dependencies) {
      this.dependencies = dependencies;

      return new ProjectJavaDependencies(this);
    }
  }

  public interface ProjectJavaDependenciesVersionsBuilder {
    ProjectJavaDependenciesDependenciesManagementBuilder versions(JavaDependenciesVersions versions);
  }

  public interface ProjectJavaDependenciesDependenciesManagementBuilder {
    ProjectJavaDependenciesDependenciesBuilder dependenciesManagements(JavaDependencies dependenciesManagement);
  }

  public interface ProjectJavaDependenciesDependenciesBuilder {
    ProjectJavaDependencies dependencies(JavaDependencies dependencies);
  }
}
