package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommands;
import tech.jhipster.lite.module.domain.javadependency.CurrentJavaDependenciesVersions;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesCurrentVersionsRepository;
import tech.jhipster.lite.module.domain.javadependency.ProjectJavaDependenciesRepository;

public class JHipsterModulesDomainService {

  private final JHipsterModulesRepository modules;
  private final JavaDependenciesCurrentVersionsRepository currentVersions;
  private final ProjectJavaDependenciesRepository projectDependencies;

  public JHipsterModulesDomainService(
    JHipsterModulesRepository modules,
    JavaDependenciesCurrentVersionsRepository currentVersions,
    ProjectJavaDependenciesRepository projectDependencies
  ) {
    this.modules = modules;
    this.currentVersions = currentVersions;
    this.projectDependencies = projectDependencies;
  }

  public void apply(JHipsterModule module) {
    Assert.notNull("module", module);

    CurrentJavaDependenciesVersions versions = currentVersions.get();

    JHipsterModuleChanges changes = JHipsterModuleChanges
      .builder()
      .projectFolder(module.projectFolder())
      .indentation(module.indentation())
      .files(module.templatedFiles())
      .mandatoryReplacements(module.mandatoryReplacements())
      .optionalReplacements(module.optionalReplacements())
      .javaBuildCommands(buildDependenciesChanges(versions, module).merge(buildPluginsChanges(versions, module)))
      .packageJson(module.packageJson())
      .preActions(module.preActions())
      .postActions(module.postActions())
      .springProperties(module.springProperties());

    modules.apply(changes);
  }

  private JavaBuildCommands buildDependenciesChanges(CurrentJavaDependenciesVersions versions, JHipsterModule module) {
    return module.javaDependencies().buildChanges(versions, projectDependencies.get(module.projectFolder()));
  }

  private JavaBuildCommands buildPluginsChanges(CurrentJavaDependenciesVersions versions, JHipsterModule module) {
    return module.javaBuildPlugins().buildChanges(versions);
  }
}
