package tech.jhipster.lite.module.domain.javadependency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommand;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommands;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;

public class JHipsterModuleJavaDependencies {

  private final Collection<JavaDependencyVersion> versions;
  private final Collection<DependencyId> dependenciesToRemove;
  private final Collection<JavaDependencyManagement> dependenciesManagement;
  private final Collection<DirectJavaDependency> dependencies;

  private JHipsterModuleJavaDependencies(JHipsterModuleJavaDependenciesBuilder builder) {
    versions = builder.versions;
    dependenciesToRemove = builder.dependenciesToRemove;
    dependenciesManagement = builder.dependenciesManagement;
    dependencies = builder.dependencies;
  }

  public static JHipsterModuleJavaDependenciesBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleJavaDependenciesBuilder(module);
  }

  public JavaBuildCommands buildChanges(CurrentJavaDependenciesVersions currentVersions, ProjectJavaDependencies projectDependencies) {
    Assert.notNull("currentVersion", currentVersions);
    Assert.notNull("projectDependencies", projectDependencies);

    return Stream
      .of(
        settedVersionsCommands(),
        dependenciesToRemoveCommands(),
        dependenciesManagementChanges(currentVersions, projectDependencies),
        dependenciesChanges(currentVersions, projectDependencies)
      )
      .flatMap(Function.identity())
      .reduce(JavaBuildCommands.EMPTY, JavaBuildCommands::merge);
  }

  private Stream<JavaBuildCommands> settedVersionsCommands() {
    return Stream.of(new JavaBuildCommands(versions.stream().map(toSetVersionCommand()).toList()));
  }

  private Function<JavaDependencyVersion, JavaBuildCommand> toSetVersionCommand() {
    return (Function<JavaDependencyVersion, JavaBuildCommand>) SetVersion::new;
  }

  private Stream<JavaBuildCommands> dependenciesToRemoveCommands() {
    return Stream.of(new JavaBuildCommands(dependenciesToRemove.stream().map(toDependencyToRemove()).toList()));
  }

  private Function<DependencyId, JavaBuildCommand> toDependencyToRemove() {
    return (Function<DependencyId, JavaBuildCommand>) RemoveDirectJavaDependency::new;
  }

  private Stream<JavaBuildCommands> dependenciesManagementChanges(
    CurrentJavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies
  ) {
    return dependenciesManagement.stream().map(dependency -> dependency.changeCommands(currentVersions, projectDependencies));
  }

  private Stream<JavaBuildCommands> dependenciesChanges(
    CurrentJavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies
  ) {
    return dependencies.stream().map(dependency -> dependency.changeCommands(currentVersions, projectDependencies));
  }

  public static class JHipsterModuleJavaDependenciesBuilder {

    private static final String DEPENDENCY = "dependency";

    private final JHipsterModuleBuilder module;
    private final Collection<JavaDependencyVersion> versions = new ArrayList<>();
    private final Collection<DependencyId> dependenciesToRemove = new ArrayList<>();
    private final Collection<DirectJavaDependency> dependencies = new ArrayList<>();
    private final Collection<JavaDependencyManagement> dependenciesManagement = new ArrayList<>();

    private JHipsterModuleJavaDependenciesBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleJavaDependenciesBuilder removeDependency(DependencyId dependency) {
      Assert.notNull(DEPENDENCY, dependency);

      dependenciesToRemove.add(dependency);

      return this;
    }

    public JHipsterModuleJavaDependenciesBuilder addDependency(GroupId groupId, ArtifactId artifactId) {
      return addDependency(groupId, artifactId, null);
    }

    public JHipsterModuleJavaDependenciesBuilder addDependency(GroupId groupId, ArtifactId artifactId, VersionSlug versionSlug) {
      JavaDependency dependency = JavaDependency.builder().groupId(groupId).artifactId(artifactId).versionSlug(versionSlug).build();

      return addDependency(dependency);
    }

    public JHipsterModuleJavaDependenciesBuilder addDependency(JavaDependency dependency) {
      Assert.notNull(DEPENDENCY, dependency);

      dependencies.add(new DirectJavaDependency(dependency));

      return this;
    }

    public JHipsterModuleJavaDependenciesBuilder setVersion(JavaDependencyVersion version) {
      Assert.notNull("version", version);

      versions.add(version);

      return this;
    }

    public JHipsterModuleJavaDependenciesBuilder addDependencyManagement(JavaDependency dependency) {
      Assert.notNull(DEPENDENCY, dependency);

      dependenciesManagement.add(new JavaDependencyManagement(dependency));

      return this;
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    public JHipsterModuleJavaDependencies build() {
      return new JHipsterModuleJavaDependencies(this);
    }
  }
}
