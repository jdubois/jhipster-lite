package tech.jhipster.lite.generator.server.springboot.springcloud.gateway.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.springcloud.gateway.domain.GatewayModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class GatewayApplicationService {

  private final GatewayModuleFactory factory;

  public GatewayApplicationService() {
    factory = new GatewayModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
