package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.domain.SpringdocModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringdocApplicationService {

  private final SpringdocModuleFactory factory;

  public SpringdocApplicationService() {
    factory = new SpringdocModuleFactory();
  }

  public JHipsterModule buildSpringdocMvcModule(JHipsterModuleProperties properties) {
    return factory.buildModuleForMvc(properties);
  }

  public JHipsterModule buildSpringdocWebfluxModule(JHipsterModuleProperties properties) {
    return factory.buildModuleForWebflux(properties);
  }

  public JHipsterModule buildSpringdocMvcModuleWithSecurityJWT(JHipsterModuleProperties properties) {
    return factory.buildModuleWithSecurityJwtForMvc(properties);
  }

  public JHipsterModule buildSpringdocWebfluxModuleWithSecurityJWT(JHipsterModuleProperties properties) {
    return factory.buildModuleWithSecurityJwtForWebflux(properties);
  }

  public JHipsterModule buildSpringdocMvcModuleWithSecurityOAuth2(JHipsterModuleProperties properties) {
    return factory.buildModuleWithSecurityOAuth2ForMvc(properties);
  }
}
