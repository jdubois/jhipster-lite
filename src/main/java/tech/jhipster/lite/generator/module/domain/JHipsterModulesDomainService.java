package tech.jhipster.lite.generator.module.domain;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependenciesCurrentVersionsRepository;
import tech.jhipster.lite.generator.module.domain.javadependency.ProjectJavaDependenciesRepository;
import tech.jhipster.lite.generator.module.domain.javadependency.command.JavaDependenciesCommands;

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

    JHipsterModuleChanges changes = JHipsterModuleChanges
      .builder()
      .projectFolder(module.projectFolder())
      .indentation(module.indentation())
      .files(module.templatedFiles())
      .mandatoryReplacements(module.mandatoryReplacements())
      .optionalReplacements(module.optionalReplacements())
      .javaDependencies(buildDependenciesChanges(module))
      .preActions(module.preActions())
      .postActions(module.postActions())
      .springProperties(module.springProperties());

    modules.apply(changes);
  }

  private JavaDependenciesCommands buildDependenciesChanges(JHipsterModule module) {
    return module.javaDependencies().buildChanges(currentVersions.get(), projectDependencies.get(module.projectFolder()));
  }
}
