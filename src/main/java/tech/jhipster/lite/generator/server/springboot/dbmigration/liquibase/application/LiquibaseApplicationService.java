package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.domain.LiquibaseModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class LiquibaseApplicationService {

  private final LiquibaseModuleFactory factory;

  public LiquibaseApplicationService() {
    factory = new LiquibaseModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
