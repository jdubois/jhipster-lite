package tech.jhipster.lite.module.domain.javabuild.command;

public sealed interface JavaBuildCommand
  permits
    AddJavaDependencyManagement,
    RemoveJavaDependencyManagement,
    AddDirectJavaDependency,
    RemoveDirectJavaDependency,
    SetVersion,
    AddDirectJavaBuildPlugin,
    AddBuildPluginManagement {
  JavaBuildCommandType type();
}
