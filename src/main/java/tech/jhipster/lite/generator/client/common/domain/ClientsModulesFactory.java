package tech.jhipster.lite.generator.client.common.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class ClientsModulesFactory {

  private static final String CACHE_NEEDLE = "  \"cacheDirectories\":";

  private ClientsModulesFactory() {}

  public static JHipsterModuleBuilder clientModuleBuilder(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .optionalReplacements()
      .in("package.json")
      .add(lineBeforeText(CACHE_NEEDLE), jestSonar(properties.indentation()))
      .and()
      .in(".lintstagedrc.js")
      .add(lineBeforeRegex("\\['prettier --write'\\]"), "  '{src/**/,}*.{js,ts,tsx,vue}': ['eslint --fix'],")
      .and()
      .and();
  }

  private static String jestSonar(Indentation indentation) {
    return new StringBuilder()
      .append(indentation.spaces())
      .append("\"jestSonar\": {")
      .append(LINE_BREAK)
      .append(indentation.times(2))
      .append("\"reportPath\": \"target/test-results/jest\",")
      .append(LINE_BREAK)
      .append(indentation.times(2))
      .append("\"reportFile\": \"TESTS-results-sonar.xml\"")
      .append(LINE_BREAK)
      .append(indentation.spaces())
      .append("},")
      .toString();
  }
}
