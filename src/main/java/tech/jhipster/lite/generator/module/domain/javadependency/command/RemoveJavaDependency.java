package tech.jhipster.lite.generator.module.domain.javadependency.command;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.domain.javadependency.DependencyId;

public record RemoveJavaDependency(DependencyId dependency) implements JavaDependencyCommand {
  public RemoveJavaDependency {
    Assert.notNull("dependency", dependency);
  }

  @Override
  public JavaDependencyCommandType type() {
    return JavaDependencyCommandType.REMOVE;
  }
}
