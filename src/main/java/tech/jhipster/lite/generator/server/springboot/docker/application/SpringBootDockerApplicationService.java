package tech.jhipster.lite.generator.server.springboot.docker.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.docker.domain.SpringBootDockerModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringBootDockerApplicationService {

  private final SpringBootDockerModuleFactory factory;

  public SpringBootDockerApplicationService() {
    factory = new SpringBootDockerModuleFactory();
  }

  public JHipsterModule buildJibModule(JHipsterModuleProperties properties) {
    return factory.buildJibModule(properties);
  }

  public JHipsterModule buildDockerFileModule(JHipsterModuleProperties properties) {
    return factory.buildDockerFileModule(properties);
  }
}
