package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.git.domain.GitRepository;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommands;
import tech.jhipster.lite.module.domain.javadependency.CurrentJavaDependenciesVersions;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesCurrentVersionsRepository;
import tech.jhipster.lite.module.domain.javadependency.ProjectJavaDependenciesRepository;

public class JHipsterModulesApplyer {

  private final JHipsterModulesRepository modules;
  private final JavaDependenciesCurrentVersionsRepository currentVersions;
  private final ProjectJavaDependenciesRepository projectDependencies;
  private final GitRepository git;

  public JHipsterModulesApplyer(
    JHipsterModulesRepository modules,
    JavaDependenciesCurrentVersionsRepository currentVersions,
    ProjectJavaDependenciesRepository projectDependencies,
    GitRepository git
  ) {
    this.modules = modules;
    this.currentVersions = currentVersions;
    this.projectDependencies = projectDependencies;
    this.git = git;
  }

  public void apply(JHipsterModuleToApply moduleToApply) {
    Assert.notNull("moduleToApply", moduleToApply);

    CurrentJavaDependenciesVersions versions = currentVersions.get();

    JHipsterModule module = moduleToApply.module();
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

    commitIfNeeded(moduleToApply);
  }

  private void commitIfNeeded(JHipsterModuleToApply moduleToApply) {
    if (moduleToApply.commitNeeded()) {
      git.commitAll(moduleToApply.module().projectFolder(), commitMessage(moduleToApply));
    }
  }

  private String commitMessage(JHipsterModuleToApply moduleToApply) {
    return new StringBuilder().append("Apply ").append(moduleToApply.slug().get()).append(" module").toString();
  }

  private JavaBuildCommands buildDependenciesChanges(CurrentJavaDependenciesVersions versions, JHipsterModule module) {
    return module.javaDependencies().buildChanges(versions, projectDependencies.get(module.projectFolder()));
  }

  private JavaBuildCommands buildPluginsChanges(CurrentJavaDependenciesVersions versions, JHipsterModule module) {
    return module.javaBuildPlugins().buildChanges(versions);
  }
}
