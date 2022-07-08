package tech.jhipster.lite.generator.server.springboot.database.postgresql.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.generator.server.springboot.database.postgresql.domain.PostgresqlModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class PostgresqlApplicationService {

  private final PostgresqlModuleFactory factory;

  public PostgresqlApplicationService(DockerImages dockerImages) {
    factory = new PostgresqlModuleFactory(dockerImages);
  }

  public JHipsterModule build(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
