package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.application;

import java.time.Instant;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.domain.FlywayModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class FlywayApplicationService {

  private final FlywayModuleFactory factory;

  public FlywayApplicationService() {
    factory = new FlywayModuleFactory();
  }

  public JHipsterModule buildInitializationModule(JHipsterModuleProperties properties) {
    return factory.buildInitializationModule(properties, Instant.now());
  }

  public JHipsterModule buildMysqlDependencyModule(JHipsterModuleProperties properties) {
    return factory.buildMysqlDependencyModule(properties);
  }
}
