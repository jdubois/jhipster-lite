package tech.jhipster.lite.module.infrastructure.secondary;

import org.springframework.stereotype.Repository;
import tech.jhipster.lite.common.domain.ProjectFilesReader;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModuleChanges;
import tech.jhipster.lite.module.domain.JHipsterModulesRepository;
import tech.jhipster.lite.module.domain.postaction.JHipsterModuleExecutionContext;
import tech.jhipster.lite.npm.domain.NpmVersions;

@Repository
class FileSystemJHipsterModulesRepository implements JHipsterModulesRepository {

  private final FileSystemJHipsterModuleFiles files;
  private final FileSystemJavaDependenciesCommandsHandler javaDependencies;
  private final FileSystemSpringPropertiesCommandsHandler springProperties;
  private final FileSystemPackageJsonHandler packageJson;

  public FileSystemJHipsterModulesRepository(ProjectFilesReader filesReader, NpmVersions npmVersions) {
    files = new FileSystemJHipsterModuleFiles(filesReader);
    javaDependencies = new FileSystemJavaDependenciesCommandsHandler();
    springProperties = new FileSystemSpringPropertiesCommandsHandler();
    packageJson = new FileSystemPackageJsonHandler(npmVersions);
  }

  @Override
  public void apply(JHipsterModuleChanges changes) {
    Assert.notNull("changes", changes);

    changes.preActions().run();

    files.create(changes.projectFolder(), changes.files());
    javaDependencies.handle(changes.indentation(), changes.projectFolder(), changes.javaBuildCommands());
    springProperties.handle(changes.projectFolder(), changes.springProperties());
    packageJson.handle(changes.indentation(), changes.projectFolder(), changes.packageJson());

    changes.mandatoryReplacements().apply(changes.projectFolder());
    changes.optionalReplacements().apply(changes.projectFolder());
    changes.postActions().run(new JHipsterModuleExecutionContext(changes.projectFolder()));
  }
}
