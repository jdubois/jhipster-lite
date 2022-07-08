package tech.jhipster.lite.module.domain.javabuild.command;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;

public record AddJavaDependencyManagement(JavaDependency dependency) implements JavaBuildCommand, AddJavaDependency {
  public AddJavaDependencyManagement {
    Assert.notNull("dependency", dependency);
  }

  @Override
  public JavaBuildCommandType type() {
    return JavaBuildCommandType.ADD_DEPENDENCY_MANAGEMENT;
  }
}
