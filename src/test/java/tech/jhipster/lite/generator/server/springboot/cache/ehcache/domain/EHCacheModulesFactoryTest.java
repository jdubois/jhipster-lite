package tech.jhipster.lite.generator.server.springboot.cache.ehcache.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.ModuleAsserter;

@UnitTest
class EHCacheModulesFactoryTest {

  private static final EHCacheModulesFactory factory = new EHCacheModulesFactory();

  @Test
  void shouldBuildJavaConfigurationModule() {
    JHipsterModule module = factory.buildJavaConfigurationModule(properties());

    commonEHCacheModuleAsserter(module)
      .createFile("src/main/java/com/jhipster/test/technical/infrastructure/secondary/cache/CacheConfiguration.java")
      .containing("JCacheManagerCustomizer")
      .and()
      .createFiles("src/main/java/com/jhipster/test/technical/infrastructure/secondary/cache/EhcacheProperties.java")
      .createPrefixedFiles(
        "src/test/java/com/jhipster/test/technical/infrastructure/secondary/cache",
        "CacheConfigurationIT.java",
        "CacheConfigurationTest.java"
      )
      .createFile("src/main/resources/config/application.properties")
      .containing("application.cache.ehcache.max-entries=100")
      .containing("application.cache.ehcache.time-to-live-seconds=3600");
  }

  @Test
  void shouldBuildXmlConfigurationModule() {
    JHipsterModule module = factory.buildXmlConfigurationModule(properties());

    commonEHCacheModuleAsserter(module)
      .createFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>jakarta.xml.bind</groupId>
              <artifactId>jakarta.xml.bind-api</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.glassfish.jaxb</groupId>
              <artifactId>jaxb-runtime</artifactId>
              <scope>runtime</scope>
            </dependency>
        """
      )
      .and()
      .createFile("src/main/java/com/jhipster/test/technical/infrastructure/secondary/cache/CacheConfiguration.java")
      .notContaining("JCacheManagerCustomizer")
      .and()
      .createFiles("src/main/resources/config/ehcache/ehcache.xml")
      .createFile("src/main/resources/config/application.properties")
      .containing("spring.cache.jcache.config=classpath:config/ehcache/ehcache.xml");
  }

  private ModuleAsserter commonEHCacheModuleAsserter(JHipsterModule module) {
    return assertThatModuleWithFiles(module, pomFile(), lockbackFile(), testLockbackFile())
      .createFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-cache</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>javax.cache</groupId>
              <artifactId>cache-api</artifactId>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.ehcache</groupId>
              <artifactId>ehcache</artifactId>
            </dependency>
        """
      )
      .and()
      .createFile("src/main/resources/logback-spring.xml")
      .containing("<logger name=\"org.ehcache\" level=\"WARN\" />")
      .and()
      .createFile("src/test/resources/logback.xml")
      .containing("<logger name=\"org.ehcache\" level=\"WARN\" />")
      .and();
  }

  private JHipsterModuleProperties properties() {
    return JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).basePackage("com.jhipster.test").build();
  }
}
