package tech.jhipster.lite.generator.server.springboot.database.mysql.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.database.mysql.domain.MySQLModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class MySQLApplicationService {

  private final MySQLModuleFactory factory;

  public MySQLApplicationService(DockerImages dockerImages) {
    factory = new MySQLModuleFactory(dockerImages);
  }

  public JHipsterModule build(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
