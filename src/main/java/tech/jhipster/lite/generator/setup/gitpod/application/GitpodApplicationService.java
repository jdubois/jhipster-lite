package tech.jhipster.lite.generator.setup.gitpod.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.setup.gitpod.domain.GitpodModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class GitpodApplicationService {

  private final GitpodModuleFactory factory;

  public GitpodApplicationService() {
    factory = new GitpodModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
