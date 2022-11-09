package tech.jhipster.lite.generator.server.sonarqube.application;

import org.springframework.stereotype.Component;
import tech.jhipster.lite.generator.server.sonarqube.domain.SonarQubeModulesFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Component
public class SonarQubeApplicationService {

  private final SonarQubeModulesFactory factory;

  public SonarQubeApplicationService(DockerImages dockerImages) {
    factory = new SonarQubeModulesFactory(dockerImages);
  }

  public JHipsterModule buildBackendModule(JHipsterModuleProperties properties) {
    return factory.buildBackendModule(properties);
  }

  public JHipsterModule buildBackendFrontendModule(JHipsterModuleProperties properties) {
    return factory.buildBackendFrontendModule(properties);
  }
}
