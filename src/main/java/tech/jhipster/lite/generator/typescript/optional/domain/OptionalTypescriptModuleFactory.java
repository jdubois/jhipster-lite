package tech.jhipster.lite.generator.typescript.optional.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.from;
import static tech.jhipster.lite.module.domain.JHipsterModule.moduleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModule.to;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class OptionalTypescriptModuleFactory {

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .files()
      .add(from("typescript/webapp/common/domain/optional/").file("Optional.ts"), to("src/main/webapp/app/common/domain/Optional.ts"))
      .add(from("typescript/test/javascript/spec/common/domain/optional/").file("Optional.spec.ts"), to("src/test/javascript/spec/common/domain/Optional.spec.ts"))
      .and()
      .build();
    //@formatter:on
  }
}
