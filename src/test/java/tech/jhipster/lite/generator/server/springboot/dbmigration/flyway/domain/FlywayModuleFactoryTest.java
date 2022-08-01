package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import java.time.Instant;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class FlywayModuleFactoryTest {

  private static final Instant INVOCATION_DATE = Instant.parse("2007-12-03T10:15:30.00Z");

  private static final FlywayModuleFactory factory = new FlywayModuleFactory();

  @Test
  void shouldBuildModuleWithoutMysqlDependency() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .put("addFlywayMysql", false)
      .build();

    JHipsterModule module = factory.buildModule(properties, INVOCATION_DATE);

    defaultFlywayModuleAsserter(module)
      .createFile("pom.xml")
      .notContaining(
        """
            <dependency>
              <groupId>org.flywaydb</groupId>
              <artifactId>flyway-mysql</artifactId>
              <version>${flyway.version}</version>
            </dependency>
        """
      );
  }

  @Test
  void shouldBuildModuleWithMysqlDependency() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .put("addFlywayMysql", true)
      .build();

    JHipsterModule module = factory.buildModule(properties, INVOCATION_DATE);

    defaultFlywayModuleAsserter(module)
      .createFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.flywaydb</groupId>
              <artifactId>flyway-mysql</artifactId>
              <version>${flyway.version}</version>
            </dependency>
        """
      );
  }

  private ModuleAsserter defaultFlywayModuleAsserter(JHipsterModule module) {
    return assertThatModuleWithFiles(module, pomFile())
      .createFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.flywaydb</groupId>
              <artifactId>flyway-core</artifactId>
              <version>${flyway.version}</version>
            </dependency>
        """
      )
      .and()
      .createFiles("src/main/resources/db/migration/V20071203101530__init.sql")
      .createFile("src/main/resources/config/application.properties")
      .containing("spring.flyway.enabled=true")
      .containing("spring.flyway.locations=classpath:db/migration")
      .and();
  }
}
