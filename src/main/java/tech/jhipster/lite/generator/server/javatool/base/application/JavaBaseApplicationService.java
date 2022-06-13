package tech.jhipster.lite.generator.server.javatool.base.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.module.domain.JHipsterModule;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.generator.server.javatool.base.domain.JavaBaseModuleFactory;

@Service
public class JavaBaseApplicationService {

  private final JavaBaseModuleFactory factory;

  public JavaBaseApplicationService() {
    factory = new JavaBaseModuleFactory();
  }

  public JHipsterModule build(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
