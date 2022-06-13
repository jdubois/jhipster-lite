package tech.jhipster.lite.generator.server.javatool.base.domain;

import static tech.jhipster.lite.generator.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.module.domain.JHipsterModule;
import tech.jhipster.lite.generator.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class JavaBaseModuleFactoryTest {

  private static final JavaBaseModuleFactory factory = new JavaBaseModuleFactory();

  @Test
  void shouldCreateModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(FileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .createPrefixedFiles("src/main/java/com/jhipster/test", "BusinessContext.java", "SharedKernel.java")
      .createPrefixedFiles(
        "src/main/java/com/jhipster/test/error/domain",
        "Assert.java",
        "MissingMandatoryValueException.java",
        "AssertionException.java",
        "NotAfterTimeException.java",
        "NotBeforeTimeException.java",
        "NullElementInCollectionException.java",
        "NumberValueTooHighException.java",
        "NumberValueTooLowException.java",
        "StringTooLongException.java",
        "StringTooShortException.java",
        "TooManyElementsException.java"
      )
      .createJavaTests(
        "com/jhipster/test/error/domain/AssertTest.java",
        "com/jhipster/test/error/domain/MissingMandatoryValueExceptionTest.java",
        "com/jhipster/test/common/domain/MyappCollectionsTest.java",
        "com/jhipster/test/UnitTest.java",
        "com/jhipster/test/ComponentTest.java",
        "com/jhipster/test/ReplaceCamelCase.java"
      )
      .createFile("src/main/java/com/jhipster/test/error/package-info.java")
      .and()
      .createFile("src/main/java/com/jhipster/test/common/package-info.java")
      .and()
      .createFile("src/main/java/com/jhipster/test/common/domain/MyappCollections.java")
      .containing("class MyappCollections");
  }
}
