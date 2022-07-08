package tech.jhipster.lite.generator.server.javatool.base.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class JavaBaseModuleFactoryTest {

  private static final JavaBaseModuleFactory factory = new JavaBaseModuleFactory();

  @Test
  void shouldCreateModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, readmeFile())
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
      .createFiles(
        "src/main/java/com/jhipster/test/error/package-info.java",
        "src/main/java/com/jhipster/test/common/package-info.java",
        "src/main/java/com/jhipster/test/common/domain/Generated.java"
      )
      .createFile("src/main/java/com/jhipster/test/common/domain/MyappCollections.java")
      .containing("class MyappCollections")
      .and()
      .createPrefixedFiles("documentation", "package-types.md")
      .createPrefixedFiles("documentation", "assertions.md")
      .createFile("README.md")
      .containing("[Package types](documentation/package-types.md)")
      .containing("[Assertions](documentation/assertions.md)");
  }
}
