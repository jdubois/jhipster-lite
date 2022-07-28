package tech.jhipster.lite.generator.server.springboot.dbmigration.mongock.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.dbmigration.mongock.domain.MongockModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class MongockApplicationService {

  private final MongockModuleFactory factory;

  public MongockApplicationService() {
    factory = new MongockModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
