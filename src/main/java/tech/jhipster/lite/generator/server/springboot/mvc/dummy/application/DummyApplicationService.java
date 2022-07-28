package tech.jhipster.lite.generator.server.springboot.mvc.dummy.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.dummy.domain.DummyFeatureModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class DummyApplicationService {

  private final DummyFeatureModuleFactory factory;

  public DummyApplicationService() {
    factory = new DummyFeatureModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
