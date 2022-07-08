package tech.jhipster.lite.module.domain.javadependency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommands;

public class JHipsterModuleJavaDependencies {

  private final Collection<JavaDependencyManagement> dependenciesManagement;
  private final Collection<DirectJavaDependency> dependencies;

  private JHipsterModuleJavaDependencies(JHipsterModuleJavaDependenciesBuilder builder) {
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
      .concat(
        dependenciesManagementChanges(currentVersions, projectDependencies),
        dependenciesChanges(currentVersions, projectDependencies)
      )
      .reduce(JavaBuildCommands.EMPTY, JavaBuildCommands::merge);
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

    private final JHipsterModuleBuilder module;
    private final Collection<DirectJavaDependency> dependencies = new ArrayList<>();
    private final Collection<JavaDependencyManagement> dependenciesManagement = new ArrayList<>();

    private JHipsterModuleJavaDependenciesBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleJavaDependenciesBuilder dependency(GroupId groupId, ArtifactId artifactId) {
      return dependency(groupId, artifactId, null);
    }

    public JHipsterModuleJavaDependenciesBuilder dependency(GroupId groupId, ArtifactId artifactId, VersionSlug versionSlug) {
      JavaDependency dependency = JavaDependency.builder().groupId(groupId).artifactId(artifactId).versionSlug(versionSlug).build();

      return dependency(dependency);
    }

    public JHipsterModuleJavaDependenciesBuilder dependency(JavaDependency dependency) {
      Assert.notNull("dependency", dependency);

      dependencies.add(new DirectJavaDependency(dependency));

      return this;
    }

    public JHipsterModuleJavaDependenciesBuilder dependencyManagement(JavaDependency dependency) {
      Assert.notNull("dependency", dependency);

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
