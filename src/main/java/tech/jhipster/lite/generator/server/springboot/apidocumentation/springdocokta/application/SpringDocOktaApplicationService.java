package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocokta.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocokta.domain.SpringDocOktaModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringDocOktaApplicationService {

  private final SpringDocOktaModuleFactory factory;

  public SpringDocOktaApplicationService() {
    factory = new SpringDocOktaModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
