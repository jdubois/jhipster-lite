package tech.jhipster.lite.generator.module.domain.javadependency.command;

public sealed interface JavaDependencyCommand permits AddJavaDependency, RemoveJavaDependency, SetJavaDependencyVersion {
  JavaDependencyCommandType type();
}
