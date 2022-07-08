package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.domain.SpringdocDomainService;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringdocApplicationService {

  private final SpringdocDomainService springdocDomainService;

  public SpringdocApplicationService(SpringdocDomainService springdocDomainService) {
    this.springdocDomainService = springdocDomainService;
  }

  public JHipsterModule buildSpringdocModule(JHipsterModuleProperties properties) {
    return springdocDomainService.buildSpringdocModule(properties);
  }

  public JHipsterModule buildSpringdocModuleWithSecurityJWT(JHipsterModuleProperties properties) {
    return springdocDomainService.buildSpringdocModuleWithSecurityJWT(properties);
  }
}
