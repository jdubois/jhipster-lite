package tech.jhipster.lite.generator.client.vue.core.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.vue.core.domain.VueModulesFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class VueApplicationService {

  private final VueModulesFactory factory;

  public VueApplicationService() {
    factory = new VueModulesFactory();
  }

  public JHipsterModule buildVueModule(JHipsterModuleProperties properties) {
    return factory.buildVueModule(properties);
  }

  public JHipsterModule buildPiniaModule(JHipsterModuleProperties properties) {
    return factory.buildPiniaModule(properties);
  }
}
