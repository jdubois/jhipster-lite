package tech.jhipster.lite.generator.server.springboot.mvc.zalandoproblem.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.zalandoproblem.domain.ZalandoProblemsModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class ZalandoProblemsApplicationService {

  private final ZalandoProblemsModuleFactory factory;

  public ZalandoProblemsApplicationService() {
    factory = new ZalandoProblemsModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
