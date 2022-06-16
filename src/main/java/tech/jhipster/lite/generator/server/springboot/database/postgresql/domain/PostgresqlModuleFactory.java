package tech.jhipster.lite.generator.server.springboot.database.postgresql.domain;

import static tech.jhipster.lite.common.domain.WordUtils.LF;
import static tech.jhipster.lite.generator.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.NEEDLE_LOGBACK_LOGGER;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.docker.domain.DockerImage;
import tech.jhipster.lite.generator.docker.domain.DockerImages;
import tech.jhipster.lite.generator.module.domain.JHipsterDestination;
import tech.jhipster.lite.generator.module.domain.JHipsterModule;
import tech.jhipster.lite.generator.module.domain.JHipsterSource;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.generator.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.generator.project.domain.DatabaseType;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;

public class PostgresqlModuleFactory {

  private static final String SOURCE = "server/springboot/database/postgresql";
  private static final String DEST_SECONDARY = "technical/infrastructure/secondary/postgresql";
  public static final String POSTGRESQL_DOCKER_IMAGE_NAME = "postgres";
  public static final String SRC_MAIN_DOCKER = "src/main/docker";
  public static final String MAIN_RESOURCES = "src/main/resources";
  public static final String LOGGING_TEST_CONFIGURATION = "logback.xml";
  public static final String ORG_POSTGRESQL = "org.postgresql";

  private final DockerImages dockerImages;

  public PostgresqlModuleFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.basePackage().path();
    String applicationName = properties.projectBaseName().capitalized();
    JHipsterSource source = from(SOURCE);
    DockerImage postgresqlDockerImage = dockerImages.get(POSTGRESQL_DOCKER_IMAGE_NAME);
    JHipsterDestination databasePath = toSrcMainJava().append(packagePath).append(DEST_SECONDARY);

    //@formatter:off
    return moduleBuilder(properties)
      .context()
      .packageName(properties.basePackage())
        .put("applicationName", applicationName)
        .put("srcDocker", SRC_MAIN_DOCKER)
        .put("postgresqlDockerImageWithVersion", postgresqlDockerImage.fullName())
        .and()
      .documentation(documentationTitle("Postgresql"), from("server/springboot/database/postgresql/postgresql.md.mustache"))
      .files()
        .add(source.template("DatabaseConfiguration.java"), databasePath.append("DatabaseConfiguration.java"))
        .add(source.template("FixedPostgreSQL10Dialect.java"), databasePath.append("FixedPostgreSQL10Dialect.java"))
        .add(source.template("FixedPostgreSQL10DialectTest.java"), toSrcTestJava().append(packagePath).append(DEST_SECONDARY).append("FixedPostgreSQL10DialectTest.java"))
        .add(source.template("postgresql.yml"), toSrcMainDocker().append("postgresql.yml"))
        .and()
      .javaDependencies()
        .add(springDataJpa())
        .add(psqlDriver())
        .add(psqlHikari())
        .add(psqlHibernateCore())
        .add(testContainer())
        .and()
      .springMainProperties()
        .set(springDatasourceUrl(), propertyValue("jdbc:postgresql://localhost:5432/" + properties.projectBaseName().name()))
        .set(springDatasourceUsername(), propertyValue(properties.projectBaseName().name()))
        .set(springDatasourcePassword(), propertyValue(""))
        .set(springDatasourceDriverClassName(), propertyValue("org.postgresql.Driver"))
        .set(propertyKey("spring.jpa.database-platform"), propertyValue(properties.basePackage().basePackage() + ".technical.infrastructure.secondary.postgresql.FixedPostgreSQL10Dialect"))
        .and()
      .springTestProperties()
        .set(springDatasourceDriverClassName(), propertyValue("org.testcontainers.jdbc.ContainerDatabaseDriver"))
        .set(springDatasourceUrl(), propertyValue("jdbc:tc:postgresql:" + postgresqlDockerImage.version()+ ":///" + properties.projectBaseName().name() + "?TC_TMPFS=/testtmpfs:rw"))
        .set(springDatasourceUsername(), propertyValue(properties.projectBaseName().name()))
        .set(springDatasourcePassword(), propertyValue(""))
        .set(propertyKey("spring.datasource.hikari.maximum-pool-size"), propertyValue("2"))
        .and()
      .optionalReplacements()
        .in("src/main/resources/logback-spring.xml")
          .add(text(NEEDLE_LOGBACK_LOGGER), logger(ORG_POSTGRESQL, Level.WARN))
          .and()
        .in("src/test/resources/logback.xml")
          .add(text(NEEDLE_LOGBACK_LOGGER), logger(ORG_POSTGRESQL, Level.WARN))
          .add(text(NEEDLE_LOGBACK_LOGGER), logger("com.github.dockerjava", Level.WARN))
          .add(text(NEEDLE_LOGBACK_LOGGER), logger("org.testcontainers", Level.WARN))
          .add(text(NEEDLE_LOGBACK_LOGGER), logger("org.jboss.logging", Level.WARN))
          .and()
        .and()
      .build();
    //@formatter:on
  }

  private PropertyKey springDatasourceUrl() {
    return propertyKey("spring.datasource.url");
  }

  private PropertyKey springDatasourceUsername() {
    return propertyKey("spring.datasource.username");
  }

  private PropertyKey springDatasourcePassword() {
    return propertyKey("spring.datasource.password");
  }

  private PropertyKey springDatasourceDriverClassName() {
    return propertyKey("spring.datasource.driver-class-name");
  }

  private String logger(String loggerName, Level level) {
    return String.format("<logger name=\"%s\" level=\"%s\" />", loggerName, level.toString()) + LF + "  " + NEEDLE_LOGBACK_LOGGER;
  }

  private JavaDependency springDataJpa() {
    return javaDependency().groupId("org.springframework.boot").artifactId("spring-boot-starter-data-jpa").build();
  }

  private JavaDependency psqlDriver() {
    return javaDependency().groupId(ORG_POSTGRESQL).artifactId("postgresql").build();
  }

  private JavaDependency psqlHikari() {
    return javaDependency().groupId("com.zaxxer").artifactId("HikariCP").build();
  }

  private JavaDependency psqlHibernateCore() {
    return javaDependency().groupId("org.hibernate").artifactId("hibernate-core").build();
  }

  private JavaDependency testContainer() {
    return javaDependency()
      .groupId("org.testcontainers")
      .artifactId(DatabaseType.POSTGRESQL.id())
      .scope(JavaDependencyScope.TEST)
      .versionSlug("testcontainers")
      .build();
  }
}
