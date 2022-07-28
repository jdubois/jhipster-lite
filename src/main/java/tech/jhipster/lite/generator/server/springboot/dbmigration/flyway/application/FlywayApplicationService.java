package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.application;

import java.time.Instant;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.domain.FlywayModuleFactory;
import tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.domain.FlywayService;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class FlywayApplicationService {

  private final FlywayService flywayService;
  private final FlywayModuleFactory factory;

  public FlywayApplicationService(FlywayService flywayService) {
    this.flywayService = flywayService;

    factory = new FlywayModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties, Instant.now());
  }

  public void addUserAuthorityChangelog(Project project) {
    flywayService.addUserAuthorityChangelog(project);
  }
}
