package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocoauth.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocoauth.domain.SpringDocOauth2ModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringdocOauth2ApplicationService {

  private final SpringDocOauth2ModuleFactory factory;

  public SpringdocOauth2ApplicationService() {
    factory = new SpringDocOauth2ModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
