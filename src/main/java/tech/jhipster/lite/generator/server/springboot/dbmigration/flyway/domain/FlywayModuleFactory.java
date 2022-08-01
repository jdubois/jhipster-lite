package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javadependency.JHipsterModuleJavaDependencies.JHipsterModuleJavaDependenciesBuilder;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class FlywayModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/dbmigration/flyway/resources");

  private static final DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(ZoneOffset.UTC);

  private static final GroupId FLYWAY_GROUP_ID = groupId("org.flywaydb");
  private static final VersionSlug FLYWAY_VERSION = versionSlug("flyway");

  public JHipsterModule buildModule(JHipsterModuleProperties properties, Instant date) {
    Assert.notNull("properties", properties);
    Assert.notNull("date", date);

    JHipsterModuleBuilder builder = commonBuilder(properties, date);

    if (needMysqlDependency(properties)) {
      addMysqlDependency(builder);
    }

    return builder.build();
  }

  private JHipsterModuleBuilder commonBuilder(JHipsterModuleProperties properties, Instant date) {
    //@formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(FLYWAY_GROUP_ID, artifactId("flyway-core"), FLYWAY_VERSION)
        .and()
      .files()
        .add(SOURCE.file("V00000000000000__init.sql"), to("src/main/resources/db/migration").append(initFilename(date)))
        .and()
      .springMainProperties()
        .set(propertyKey("spring.flyway.enabled"), propertyValue("true"))
        .set(propertyKey("spring.flyway.locations"), propertyValue("classpath:db/migration"))
        .and();
    //@formatter:on
  }

  private boolean needMysqlDependency(JHipsterModuleProperties properties) {
    return properties.getOrDefaultBoolean("addFlywayMysql", false);
  }

  private JHipsterModuleJavaDependenciesBuilder addMysqlDependency(JHipsterModuleBuilder builder) {
    return builder.javaDependencies().addDependency(FLYWAY_GROUP_ID, artifactId("flyway-mysql"), FLYWAY_VERSION);
  }

  private String initFilename(Instant date) {
    return new StringBuilder().append("V").append(FILE_DATE_FORMAT.format(date)).append("__init.sql").toString();
  }
}
