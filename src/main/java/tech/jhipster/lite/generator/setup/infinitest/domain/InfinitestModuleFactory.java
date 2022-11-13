package tech.jhipster.lite.generator.setup.infinitest.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class InfinitestModuleFactory {

  public JHipsterModule build(JHipsterModuleProperties properties) {
    return moduleBuilder(properties).files().add(from("infinitest/template-infinitest.filters"), to("infinitest.filters")).and().build();
  }
}
