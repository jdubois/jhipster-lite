package tech.jhipster.lite.module.domain.javadependency;

import java.util.Collection;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommand;

public class DirectJavaDependency extends JavaDependencyCommandsCreator {

  DirectJavaDependency(JavaDependency dependency) {
    super(dependency);
  }

  @Override
  protected Collection<JavaBuildCommand> dependencyCommands(ProjectJavaDependencies projectDependencies) {
    return dependency().dependencyCommands(DependenciesCommandsFactory.DIRECT, projectDependencies.dependency(dependency().id()));
  }
}
