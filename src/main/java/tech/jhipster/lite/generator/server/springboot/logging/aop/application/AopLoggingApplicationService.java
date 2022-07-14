package tech.jhipster.lite.generator.server.springboot.logging.aop.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.logging.aop.domain.AopLoggingModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class AopLoggingApplicationService {

  private final AopLoggingModuleFactory factory;

  public AopLoggingApplicationService() {
    factory = new AopLoggingModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
