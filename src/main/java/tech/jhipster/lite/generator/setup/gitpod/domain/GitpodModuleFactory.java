package tech.jhipster.lite.generator.setup.gitpod.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class GitpodModuleFactory {

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .batch(from("setup/gitpod"), to("."))
          .file(".gitpod.yml")
          .file(".gitpod.Dockerfile")
          .and()
        .and()
      .build();
    //@formatter:on
  }
}
