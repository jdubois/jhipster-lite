package tech.jhipster.lite.module.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.git.domain.GitRepository;
import tech.jhipster.lite.module.domain.JHipsterModuleToApply;
import tech.jhipster.lite.module.domain.JHipsterModulesApplyer;
import tech.jhipster.lite.module.domain.JHipsterModulesRepository;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesCurrentVersionsRepository;
import tech.jhipster.lite.module.domain.javadependency.ProjectJavaDependenciesRepository;

@Service
public class JHipsterModulesApplicationService {

  private final JHipsterModulesApplyer applyer;

  public JHipsterModulesApplicationService(
    JHipsterModulesRepository modulesRepository,
    JavaDependenciesCurrentVersionsRepository currentVersions,
    ProjectJavaDependenciesRepository projectDependencies,
    GitRepository git
  ) {
    applyer = new JHipsterModulesApplyer(modulesRepository, currentVersions, projectDependencies, git);
  }

  public void apply(JHipsterModuleToApply module) {
    applyer.apply(module);
  }
}
