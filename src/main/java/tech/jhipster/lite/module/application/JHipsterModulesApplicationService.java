package tech.jhipster.lite.module.application;

import java.time.Instant;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.git.domain.GitRepository;
import tech.jhipster.lite.module.domain.JHipsterModuleApplied;
import tech.jhipster.lite.module.domain.JHipsterModuleEvents;
import tech.jhipster.lite.module.domain.JHipsterModuleToApply;
import tech.jhipster.lite.module.domain.JHipsterModulesApplyer;
import tech.jhipster.lite.module.domain.JHipsterModulesRepository;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesCurrentVersionsRepository;
import tech.jhipster.lite.module.domain.javadependency.ProjectJavaDependenciesRepository;

@Service
public class JHipsterModulesApplicationService {

  private final JHipsterModulesApplyer applyer;
  private final JHipsterModuleEvents events;

  public JHipsterModulesApplicationService(
    JHipsterModulesRepository modulesRepository,
    JHipsterModuleEvents events,
    JavaDependenciesCurrentVersionsRepository currentVersions,
    ProjectJavaDependenciesRepository projectDependencies,
    GitRepository git
  ) {
    applyer = new JHipsterModulesApplyer(modulesRepository, currentVersions, projectDependencies, git);
    this.events = events;
  }

  public void apply(JHipsterModuleToApply module) {
    applyer.apply(module);

    events.dispatch(new JHipsterModuleApplied(module.properties(), module.slug(), Instant.now()));
  }
}
