package tech.jhipster.lite.generator.server.javatool.jacoco.application;

import org.springframework.stereotype.Component;
import tech.jhipster.lite.generator.server.javatool.jacoco.domain.JacocoThresholdModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Component
public class JacocoApplicationService {

  private final JacocoThresholdModuleFactory factory;

  public JacocoApplicationService() {
    factory = new JacocoThresholdModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
