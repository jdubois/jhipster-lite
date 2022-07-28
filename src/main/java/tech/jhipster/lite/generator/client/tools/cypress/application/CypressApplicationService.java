package tech.jhipster.lite.generator.client.tools.cypress.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.tools.cypress.domain.CypressModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class CypressApplicationService {

  private final CypressModuleFactory factory;

  public CypressApplicationService() {
    factory = new CypressModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
