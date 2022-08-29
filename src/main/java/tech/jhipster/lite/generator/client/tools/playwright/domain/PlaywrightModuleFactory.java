package tech.jhipster.lite.generator.client.tools.playwright.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.packagejson.VersionSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class PlaywrightModuleFactory {

  private static final JHipsterSource SOURCE = from("client/common/playwright");

  private static final String PLAYWRIGHT_TESTS = "src/test/javascript/integration/common/primary/app";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDevDependency(packageName("@playwright/test"), VersionSource.COMMON)
        .addScript(scriptKey("e2e"), scriptCommand("npx playwright test --headed"))
        .addScript(scriptKey("e2e:headless"), scriptCommand("npx playwright test"))
        .and()
      .files()
        .add(SOURCE.template("playwright.config.ts"), to("playwright.config.ts"))
        .batch(SOURCE.append(PLAYWRIGHT_TESTS), to(PLAYWRIGHT_TESTS))
          .addTemplate("Home.spec.ts")
          .addTemplate("Home-Page.ts")
          .and()
        .and()
      .build();
    //@formatter:off
  }
}
