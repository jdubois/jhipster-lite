package tech.jhipster.lite.generator.module.domain.javadependency.command;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependencyVersion;

public record SetJavaDependencyVersion(JavaDependencyVersion version) implements JavaDependencyCommand {
  public SetJavaDependencyVersion {
    Assert.notNull("version", version);
  }

  @Override
  public JavaDependencyCommandType type() {
    return JavaDependencyCommandType.SET_VERSION;
  }

  public String property() {
    return version().slug().propertyName();
  }

  public String dependencyVersion() {
    return version().version().get();
  }
}
