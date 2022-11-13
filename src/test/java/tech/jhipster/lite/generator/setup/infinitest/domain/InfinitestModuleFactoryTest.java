package tech.jhipster.lite.generator.setup.infinitest.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class InfinitestModuleFactoryTest {

  private static final InfinitestModuleFactory factory = new InfinitestModuleFactory();

  @Test
  void shouldBuild() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.build(properties);

    assertThatModule(module).hasFile("infinitest.filters");
  }
}
