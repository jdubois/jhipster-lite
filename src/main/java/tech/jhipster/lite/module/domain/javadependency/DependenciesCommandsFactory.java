package tech.jhipster.lite.module.domain.javadependency;

import java.util.function.Function;
import tech.jhipster.lite.module.domain.javabuild.command.AddDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.AddJavaDependencyManagement;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommand;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveDirectJavaDependency;
import tech.jhipster.lite.module.domain.javabuild.command.RemoveJavaDependencyManagement;

class DependenciesCommandsFactory {

  public static final DependenciesCommandsFactory MANAGEMENT = new DependenciesCommandsFactory(
    AddJavaDependencyManagement::new,
    RemoveJavaDependencyManagement::new
  );
  public static final DependenciesCommandsFactory DIRECT = new DependenciesCommandsFactory(
    AddDirectJavaDependency::new,
    RemoveDirectJavaDependency::new
  );

  private final Function<JavaDependency, JavaBuildCommand> addDependency;
  private final Function<DependencyId, JavaBuildCommand> removeDependency;

  private DependenciesCommandsFactory(
    Function<JavaDependency, JavaBuildCommand> addDependency,
    Function<DependencyId, JavaBuildCommand> removeDependency
  ) {
    this.addDependency = addDependency;
    this.removeDependency = removeDependency;
  }

  public JavaBuildCommand addDependency(JavaDependency javaDependency) {
    return addDependency.apply(javaDependency);
  }

  public JavaBuildCommand removeDependency(DependencyId id) {
    return removeDependency.apply(id);
  }
}
