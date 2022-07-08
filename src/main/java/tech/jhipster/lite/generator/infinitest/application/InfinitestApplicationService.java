package tech.jhipster.lite.generator.infinitest.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.infinitest.domain.InfinitestModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class InfinitestApplicationService {

  private final InfinitestModuleFactory factory;

  public InfinitestApplicationService() {
    factory = new InfinitestModuleFactory();
  }

  public JHipsterModule build(JHipsterModuleProperties properties) {
    return factory.build(properties);
  }
}
