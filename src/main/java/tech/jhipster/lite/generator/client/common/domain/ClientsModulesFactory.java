package tech.jhipster.lite.generator.client.common.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class ClientsModulesFactory {

  private ClientsModulesFactory() {}

  public static JHipsterModuleBuilder clientModuleBuilder(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .optionalReplacements()
      .in(path("package.json"))
      .and()
      .in(path(".lintstagedrc.js"))
      .add(lineBeforeRegex("\\['prettier --write'\\]"), "  '{src/**/,}*.{js,ts,tsx,vue}': ['eslint --fix'],")
      .and()
      .and();
  }
}
