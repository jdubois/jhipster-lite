package tech.jhipster.lite.generator.module.domain.javadependency;

import java.util.ArrayList;
import java.util.Collection;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.generator.module.domain.javadependency.command.JavaDependenciesCommands;

public class JHipsterModuleJavaDependencies {

  private final Collection<JavaDependency> dependencies;

  private JHipsterModuleJavaDependencies(JHipsterModuleJavaDependenciesBuilder builder) {
    dependencies = builder.dependencies;
  }

  public static JHipsterModuleJavaDependenciesBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleJavaDependenciesBuilder(module);
  }

  public JavaDependenciesCommands buildChanges(
    CurrentJavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies
  ) {
    Assert.notNull("currentVersion", currentVersions);
    Assert.notNull("projectDependencies", projectDependencies);

    return dependencies
      .stream()
      .map(dependency -> dependency.changeCommands(currentVersions, projectDependencies))
      .reduce(JavaDependenciesCommands.EMPTY, JavaDependenciesCommands::merge);
  }

  public static class JHipsterModuleJavaDependenciesBuilder {

    private final JHipsterModuleBuilder module;
    private final Collection<JavaDependency> dependencies = new ArrayList<>();

    private JHipsterModuleJavaDependenciesBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleJavaDependenciesBuilder add(GroupId groupId, ArtifactId artifactId) {
      return add(groupId, artifactId, null);
    }

    public JHipsterModuleJavaDependenciesBuilder add(GroupId groupId, ArtifactId artifactId, VersionSlug versionSlug) {
      JavaDependency dependency = JavaDependency.builder().groupId(groupId).artifactId(artifactId).versionSlug(versionSlug).build();

      return add(dependency);
    }

    public JHipsterModuleJavaDependenciesBuilder add(JavaDependency dependency) {
      Assert.notNull("dependency", dependency);

      dependencies.add(dependency);

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
