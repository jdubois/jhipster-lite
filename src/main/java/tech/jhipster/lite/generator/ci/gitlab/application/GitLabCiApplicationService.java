package tech.jhipster.lite.generator.ci.gitlab.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.ci.gitlab.domain.GitLabCiModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class GitLabCiApplicationService {

  private final GitLabCiModuleFactory factory;

  public GitLabCiApplicationService() {
    factory = new GitLabCiModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
