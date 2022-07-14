package tech.jhipster.lite.generator.server.springboot.core.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.core.domain.SpringBootCoreModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringBootApplicationService {

  private final SpringBootCoreModuleFactory factory;

  public SpringBootApplicationService() {
    factory = new SpringBootCoreModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
