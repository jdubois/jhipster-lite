package tech.jhipster.lite.generator.client.tools.playwright.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class PlaywrightModuleFactoryTest {

  private static final PlaywrightModuleFactory factory = new PlaywrightModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .put("serverPort", 9000)
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile())
      .createFile("package.json")
      .containing(nodeDependency("@playwright/test"))
      .containing("\"e2e\": \"npx playwright test --headed\"")
      .containing("\"e2e:headless\": \"npx playwright test\"")
      .and()
      .createFile("playwright.config.ts")
      .containing("baseURL: process.env.URL || 'http://localhost:9000',")
      .and()
      .createPrefixedFiles("src/test/javascript/integration/common/primary/app", "Home.spec.ts", "Home-Page.ts");
  }
}
