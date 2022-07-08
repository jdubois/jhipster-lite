package tech.jhipster.lite.generator.server.springboot.database.mariadb.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.generator.server.springboot.database.mariadb.domain.MariaDBModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class MariaDBApplicationService {

  private final MariaDBModuleFactory factory;

  public MariaDBApplicationService(DockerImages dockerImages) {
    factory = new MariaDBModuleFactory(dockerImages);
  }

  public JHipsterModule build(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
