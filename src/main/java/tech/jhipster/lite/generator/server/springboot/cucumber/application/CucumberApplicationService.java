package tech.jhipster.lite.generator.server.springboot.cucumber.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.module.domain.JHipsterModule;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.generator.server.springboot.cucumber.domain.CucumberModuleFactory;

@Service
public class CucumberApplicationService {

  private final CucumberModuleFactory factory;

  public CucumberApplicationService() {
    factory = new CucumberModuleFactory();
  }

  public JHipsterModule build(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
