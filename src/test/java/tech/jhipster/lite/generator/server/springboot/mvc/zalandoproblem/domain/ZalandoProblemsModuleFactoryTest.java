package tech.jhipster.lite.generator.server.springboot.mvc.zalandoproblem.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class ZalandoProblemsModuleFactoryTest {

  private static final ZalandoProblemsModuleFactory factory = new ZalandoProblemsModuleFactory();

  @Test
  void shouldBuildProblemsModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .createFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.zalando</groupId>
              <artifactId>problem-spring-web</artifactId>
              <version>${problem-spring.version}</version>
            </dependency>
        """
      )
      .and()
      .createFile("src/main/resources/config/application.properties")
      .containing("application.exception.details=false")
      .containing("application.exception.package=org.,java.,net.,javax.,com.,io.,de.,com.jhipster.test")
      .and()
      .createFile("src/test/resources/config/application.properties")
      .containing("application.exception.package=org.,java.")
      .and()
      .createPrefixedFiles(
        "src/main/java/com/jhipster/test/technical/infrastructure/primary/exception",
        "BadRequestAlertException.java",
        "ErrorConstants.java",
        "ExceptionTranslator.java",
        "FieldErrorDTO.java",
        "HeaderUtil.java",
        "ProblemConfiguration.java"
      )
      .createPrefixedFiles(
        "src/test/java/com/jhipster/test/technical/infrastructure/primary/exception",
        "BadRequestAlertExceptionTest.java",
        "ExceptionTranslatorIT.java",
        "ExceptionTranslatorTestController.java",
        "FieldErrorDTOTest.java",
        "HeaderUtilTest.java"
      )
      .createJavaTests("com/jhipster/test/TestUtil.java");
  }
}
