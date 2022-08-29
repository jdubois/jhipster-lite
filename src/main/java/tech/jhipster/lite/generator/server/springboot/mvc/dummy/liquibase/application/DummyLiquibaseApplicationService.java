package tech.jhipster.lite.generator.server.springboot.mvc.dummy.liquibase.application;

import java.time.Instant;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.dummy.liquibase.domain.DummyLiquibaseModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class DummyLiquibaseApplicationService {

  private final DummyLiquibaseModuleFactory factory;

  public DummyLiquibaseApplicationService() {
    factory = new DummyLiquibaseModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties, Instant.now());
  }
}
