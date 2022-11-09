package tech.jhipster.lite.generator.server.springboot.database.postgresqldialect.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.database.postgresqldialect.domain.PostgresqlDialectModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class PostgresqlDialectApplicationService {

  private final PostgresqlDialectModuleFactory factory;

  public PostgresqlDialectApplicationService() {
    factory = new PostgresqlDialectModuleFactory();
  }

  public JHipsterModule build(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
