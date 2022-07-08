package tech.jhipster.lite.module.domain.javabuild.command;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;

public record AddDirectJavaDependency(JavaDependency dependency) implements JavaBuildCommand, AddJavaDependency {
  public AddDirectJavaDependency {
    Assert.notNull("dependency", dependency);
  }

  @Override
  public JavaBuildCommandType type() {
    return JavaBuildCommandType.ADD_DEPENDENCY;
  }
}
