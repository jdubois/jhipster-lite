package tech.jhipster.lite.generator.typescript.domain.optional;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.typescript.optional.domain.OptionalTypescriptModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class OptionalTypescriptModuleFactoryTest {

  private static final OptionalTypescriptModuleFactory factory = new OptionalTypescriptModuleFactory();

  @Test
  void shouldCreateOptionalTypescriptModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module)
      .hasFile("src/main/webapp/app/common/domain/Optional.ts")
      .and()
      .hasFile("src/test/javascript/spec/common/domain/Optional.spec.ts");
  }
}
