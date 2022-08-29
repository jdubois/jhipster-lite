package tech.jhipster.lite.generator.server.springboot.mvc.dummy.jpapersistence.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.dummy.jpapersistence.domain.DummyJpaPersistenceModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class DummyJpaPersistenceApplicationService {

  private final DummyJpaPersistenceModuleFactory factory;

  public DummyJpaPersistenceApplicationService() {
    factory = new DummyJpaPersistenceModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
