package tech.jhipster.lite.generator.init.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.init.domain.InitModuleFactory;
import tech.jhipster.lite.git.domain.GitRepository;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class InitApplicationService {

  private final InitModuleFactory factory;

  public InitApplicationService(GitRepository git) {
    factory = new InitModuleFactory(git);
  }

  public JHipsterModule buildFullInitModule(JHipsterModuleProperties properties) {
    return factory.buildFullModule(properties);
  }

  public JHipsterModule buildMinimalInitModule(JHipsterModuleProperties properties) {
    return factory.buildMinimalModule(properties);
  }
}
