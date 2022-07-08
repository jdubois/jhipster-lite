package tech.jhipster.lite.generator.server.springboot.cucumber.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.cucumber.domain.CucumberModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

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
