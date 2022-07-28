package tech.jhipster.lite.generator.server.springboot.webflux.web.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class SpringBootWebfluxModuleFactoryTest {

  private static final SpringBootWebfluxModuleFactory factory = new SpringBootWebfluxModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .put("serverPort", 9000)
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .createFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-webflux</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>io.projectreactor</groupId>
              <artifactId>reactor-test</artifactId>
              <scope>test</scope>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-validation</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.zalando</groupId>
              <artifactId>problem-spring-webflux</artifactId>
              <version>${problem-spring.version}</version>
            </dependency>
        """
      )
      .and()
      .createFile("src/main/resources/config/application.properties")
      .containing("server.port=9000")
      .containing("application.exception.details=false")
      .containing("application.exception.package=org.,java.,net.,javax.,com.,io.,de.,com.jhipster.test")
      .and()
      .createFile("src/test/resources/config/application.properties")
      .containing("server.port=0")
      .containing("application.exception.package=org.,java.")
      .and()
      .createPrefixedFiles(
        "src/main/java/com/jhipster/test/technical/infrastructure/primary/exception/",
        "ProblemConfiguration.java",
        "HeaderUtil.java",
        "BadRequestAlertException.java",
        "ErrorConstants.java",
        "ExceptionTranslator.java",
        "FieldErrorDTO.java"
      )
      .createPrefixedFiles(
        "src/test/java/com/jhipster/test/technical/infrastructure/primary/exception/",
        "HeaderUtilTest.java",
        "BadRequestAlertExceptionTest.java",
        "ExceptionTranslatorIT.java",
        "ExceptionTranslatorTestController.java",
        "FieldErrorDTOTest.java"
      )
      .createFiles("src/test/java/com/jhipster/test/TestUtil.java");
  }
}
