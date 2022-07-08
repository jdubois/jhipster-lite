package tech.jhipster.lite.generator.server.sonar.application;

import org.springframework.stereotype.Component;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.generator.server.sonar.domain.SonarModulesFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Component
public class SonarApplicationService {

  private final SonarModulesFactory factory;

  public SonarApplicationService(DockerImages dockerImages) {
    factory = new SonarModulesFactory(dockerImages);
  }

  public JHipsterModule buildBackendModule(JHipsterModuleProperties properties) {
    return factory.buildBackendModule(properties);
  }

  public JHipsterModule buildBackendFrontendModule(JHipsterModuleProperties properties) {
    return factory.buildBackendFrontendModule(properties);
  }
}
