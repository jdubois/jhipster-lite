package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocauth0.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocauth0.domain.SpringDocAuth0ModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringDocAuth0ApplicationService {

  private final SpringDocAuth0ModuleFactory factory;

  public SpringDocAuth0ApplicationService() {
    factory = new SpringDocAuth0ModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
