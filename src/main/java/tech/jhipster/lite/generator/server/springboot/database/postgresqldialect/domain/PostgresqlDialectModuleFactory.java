package tech.jhipster.lite.generator.server.springboot.database.postgresqldialect.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.server.springboot.database.common.domain.DatabaseType;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class PostgresqlDialectModuleFactory {

  private static final String DEST_SECONDARY = "technical/infrastructure/secondary/postgresql";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    JHipsterSource source = from("server/springboot/database/" + DatabaseType.POSTGRESQL.id() + "dialect");
    String packagePath = properties.packagePath();
    JHipsterDestination databasePath = toSrcMainJava().append(packagePath).append(DEST_SECONDARY);

    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .add(source.template("FixedPostgreSQL10Dialect.java"), databasePath.append("FixedPostgreSQL10Dialect.java"))
        .add(
          source.template("FixedPostgreSQL10DialectTest.java"),
          toSrcTestJava().append(packagePath).append(DEST_SECONDARY).append("FixedPostgreSQL10DialectTest.java")
        )
        .and()
      .springMainProperties()
        .set(
          propertyKey("spring.jpa.database-platform"),
          propertyValue(properties.basePackage().get() + ".technical.infrastructure.secondary.postgresql.FixedPostgreSQL10Dialect")
        )
        .and()
      .springTestProperties()
        .set(
          propertyKey("spring.jpa.database-platform"),
          propertyValue(properties.basePackage().get() + ".technical.infrastructure.secondary.postgresql.FixedPostgreSQL10Dialect")
        )
        .and()
      .build();
    //@formatter:on
  }
}
