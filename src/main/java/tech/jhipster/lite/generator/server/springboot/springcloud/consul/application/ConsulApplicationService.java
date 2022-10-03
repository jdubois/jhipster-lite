package tech.jhipster.lite.generator.server.springboot.springcloud.consul.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.springcloud.consul.domain.ConsulModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class ConsulApplicationService {

  private final ConsulModuleFactory factory;

  public ConsulApplicationService(DockerImages dockerImages) {
    this.factory = new ConsulModuleFactory(dockerImages);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
