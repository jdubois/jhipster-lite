package tech.jhipster.lite.generator.client.react.core.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.react.core.domain.ReactCoreModulesFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class ReactCoreApplicationService {

  private final ReactCoreModulesFactory factory;

  public ReactCoreApplicationService() {
    factory = new ReactCoreModulesFactory();
  }

  public JHipsterModule buildModuleWithoutStyle(JHipsterModuleProperties properties) {
    return factory.buildModuleWithoutStyle(properties);
  }

  public JHipsterModule buildModuleWithStyle(JHipsterModuleProperties properties) {
    return factory.buildModuleWithStyle(properties);
  }
}
