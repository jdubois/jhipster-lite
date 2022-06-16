package tech.jhipster.lite.generator.infinitest.domain;

import static tech.jhipster.lite.generator.module.domain.JHipsterModule.*;

import tech.jhipster.lite.generator.module.domain.JHipsterModule;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;

public class InfinitestModuleFactory {

  public JHipsterModule build(JHipsterModuleProperties properties) {
    return moduleBuilder(properties).files().add(from("infinitest/template-infinitest.filters"), to("infinitest.filters")).and().build();
  }
}
