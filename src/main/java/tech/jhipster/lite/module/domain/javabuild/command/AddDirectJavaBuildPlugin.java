package tech.jhipster.lite.module.domain.javabuild.command;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.javabuildplugin.JavaBuildPlugin;

public record AddDirectJavaBuildPlugin(JavaBuildPlugin javaBuildPlugin) implements JavaBuildCommand, AddJavaBuildPlugin {
  public AddDirectJavaBuildPlugin {
    Assert.notNull("javaBuildPlugin", javaBuildPlugin);
  }

  @Override
  public JavaBuildCommandType type() {
    return JavaBuildCommandType.ADD_DIRECT_JAVA_BUILD_PLUGIN;
  }
}
