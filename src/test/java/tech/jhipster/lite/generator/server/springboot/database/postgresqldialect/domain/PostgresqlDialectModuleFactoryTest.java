package tech.jhipster.lite.generator.server.springboot.database.postgresqldialect.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class PostgresqlDialectModuleFactoryTest {

  private static final PostgresqlDialectModuleFactory factory = new PostgresqlDialectModuleFactory();

  @Test
  void shouldAddPostgresqlDialect() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFile("src/main/java/com/jhipster/test/technical/infrastructure/secondary/postgresql/FixedPostgreSQL10Dialect.java")
      .and()
      .hasFile("src/test/java/com/jhipster/test/technical/infrastructure/secondary/postgresql/FixedPostgreSQL10DialectTest.java")
      .and()
      .hasFile("src/main/resources/config/application.properties")
      .containing("spring.jpa.database-platform=com.jhipster.test.technical.infrastructure.secondary.postgresql.FixedPostgreSQL10Dialect")
      .and()
      .hasFile("src/test/resources/config/application.properties")
      .containing("spring.jpa.database-platform=com.jhipster.test.technical.infrastructure.secondary.postgresql.FixedPostgreSQL10Dialect");
  }
}
