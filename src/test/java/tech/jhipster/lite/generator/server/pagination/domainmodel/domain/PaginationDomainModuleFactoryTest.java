package tech.jhipster.lite.generator.server.pagination.domainmodel.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class PaginationDomainModuleFactoryTest {

  private static final PaginationDomainModuleFactory factory = new PaginationDomainModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myApp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .hasFiles("src/main/java/com/jhipster/test/pagination/package-info.java")
      .hasPrefixedFiles("src/main/java/com/jhipster/test/pagination/domain", "MyAppPage.java", "MyAppPageable.java")
      .hasPrefixedFiles(
        "src/test/java/com/jhipster/test/pagination/domain",
        "MyAppPageTest.java",
        "MyAppPageableTest.java",
        "MyAppPagesFixture.java"
      );
  }
}
