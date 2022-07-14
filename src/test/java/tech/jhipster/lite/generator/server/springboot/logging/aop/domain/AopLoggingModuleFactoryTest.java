package tech.jhipster.lite.generator.server.springboot.logging.aop.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class AopLoggingModuleFactoryTest {

  private static final AopLoggingModuleFactory factory = new AopLoggingModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .createFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-aop</artifactId>
            </dependency>
        """
      )
      .and()
      .createPrefixedFiles(
        "src/main/java/com/jhipster/test/technical/infrastructure/secondary/aop/logging",
        "LoggingAspectConfiguration.java",
        "LoggingAspect.java"
      )
      .createFiles("src/test/java/com/jhipster/test/technical/infrastructure/secondary/aop/logging/LoggingAspectTest.java")
      .createFile("src/main/resources/config/application.properties")
      .containing("application.aop.logging=false")
      .and()
      .createFile("src/main/resources/config/application-local.properties")
      .containing("application.aop.logging=true")
      .and()
      .createFile("src/test/resources/config/application.properties")
      .containing("application.aop.logging=true")
      .and();
  }
}
