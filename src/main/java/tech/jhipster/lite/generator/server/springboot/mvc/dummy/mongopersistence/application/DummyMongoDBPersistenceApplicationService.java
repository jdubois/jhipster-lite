package tech.jhipster.lite.generator.server.springboot.mvc.dummy.mongopersistence.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.dummy.mongopersistence.domain.DummyMongoDBPersistenceModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class DummyMongoDBPersistenceApplicationService {

  private final DummyMongoDBPersistenceModuleFactory factory;

  public DummyMongoDBPersistenceApplicationService() {
    factory = new DummyMongoDBPersistenceModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
