package tech.jhipster.lite.generator.client.angular.admin.health.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.angular.admin.health.domain.AngularHealthModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class AngularHealthApplicationService {

  private final AngularHealthModuleFactory factory;

  public AngularHealthApplicationService() {
    factory = new AngularHealthModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
