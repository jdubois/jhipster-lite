package tech.jhipster.lite.generator.server.springboot.database.postgresql.domain;

import static tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonModuleBuilder.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.server.springboot.database.common.domain.DatabaseType;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.LogLevel;
import tech.jhipster.lite.module.domain.docker.DockerImageVersion;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class PostgresqlModuleFactory {

  private static final String DEST_SECONDARY = "technical/infrastructure/secondary/postgresql";
  public static final String ORG_POSTGRESQL = "org.postgresql";

  private final DockerImages dockerImages;

  public PostgresqlModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    DockerImageVersion dockerImage = dockerImages.get("postgres");
    JHipsterSource source = from("server/springboot/database/" + DatabaseType.POSTGRESQL.id());
    String packagePath = properties.packagePath();
    JHipsterDestination databasePath = toSrcMainJava().append(packagePath).append(DEST_SECONDARY);

    return sqlCommonModuleBuilder(
      properties,
      DatabaseType.POSTGRESQL,
      dockerImage,
      documentationTitle("Postgresql"),
      artifactId("postgresql")
    )
      .files()
      .add(source.template("FixedPostgreSQL10Dialect.java"), databasePath.append("FixedPostgreSQL10Dialect.java"))
      .add(
        source.template("FixedPostgreSQL10DialectTest.java"),
        toSrcTestJava().append(packagePath).append(DEST_SECONDARY).append("FixedPostgreSQL10DialectTest.java")
      )
      .and()
      .javaDependencies()
      .addDependency(groupId(ORG_POSTGRESQL), artifactId("postgresql"))
      .and()
      .springMainProperties()
      .set(propertyKey("spring.datasource.url"), propertyValue("jdbc:postgresql://localhost:5432/" + properties.projectBaseName().name()))
      .set(propertyKey("spring.datasource.username"), propertyValue(properties.projectBaseName().name()))
      .set(propertyKey("spring.datasource.driver-class-name"), propertyValue("org.postgresql.Driver"))
      .set(
        propertyKey("spring.jpa.database-platform"),
        propertyValue(properties.basePackage().get() + ".technical.infrastructure.secondary.postgresql.FixedPostgreSQL10Dialect")
      )
      .and()
      .springTestProperties()
      .set(
        propertyKey("spring.datasource.url"),
        propertyValue(
          "jdbc:tc:postgresql:" + dockerImage.version().get() + ":///" + properties.projectBaseName().name() + "?TC_TMPFS=/testtmpfs:rw"
        )
      )
      .and()
      .springMainLogger(ORG_POSTGRESQL, LogLevel.WARN)
      .springTestLogger(ORG_POSTGRESQL, LogLevel.WARN)
      .springTestLogger("org.jboss.logging", LogLevel.WARN)
      .build();
  }
}
