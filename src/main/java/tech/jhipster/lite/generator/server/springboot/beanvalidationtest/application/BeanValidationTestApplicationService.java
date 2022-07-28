package tech.jhipster.lite.generator.server.springboot.beanvalidationtest.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.beanvalidationtest.domain.BeanValidationTestModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class BeanValidationTestApplicationService {

  private final BeanValidationTestModuleFactory factory;

  public BeanValidationTestApplicationService() {
    factory = new BeanValidationTestModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
