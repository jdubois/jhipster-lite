package tech.jhipster.lite.generator.server.pagination.jpa.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.pagination.jpa.domain.JpaPaginationModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class JpaPaginationModuleApplicationService {

  private final JpaPaginationModuleFactory factory;

  public JpaPaginationModuleApplicationService() {
    factory = new JpaPaginationModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
