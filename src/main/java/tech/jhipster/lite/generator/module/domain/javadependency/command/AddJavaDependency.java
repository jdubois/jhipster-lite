package tech.jhipster.lite.generator.module.domain.javadependency.command;

import java.util.Optional;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.domain.javadependency.ArtifactId;
import tech.jhipster.lite.generator.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.generator.module.domain.javadependency.GroupId;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.generator.module.domain.javadependency.VersionSlug;

public record AddJavaDependency(JavaDependency dependency) implements JavaDependencyCommand {
  public AddJavaDependency {
    Assert.notNull("dependency", dependency);
  }

  @Override
  public JavaDependencyCommandType type() {
    return JavaDependencyCommandType.ADD;
  }

  public GroupId groupId() {
    return dependency().groupId();
  }

  public ArtifactId artifactId() {
    return dependency().artifactId();
  }

  public Optional<VersionSlug> version() {
    return dependency().version();
  }

  public JavaDependencyScope scope() {
    return dependency().scope();
  }

  public boolean optional() {
    return dependency().optional();
  }

  public DependencyId dependencyId() {
    return dependency().id();
  }
}
