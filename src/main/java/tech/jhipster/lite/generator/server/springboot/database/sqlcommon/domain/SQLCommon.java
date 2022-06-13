package tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain;

import java.util.Map;
import java.util.TreeMap;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;

public class SQLCommon {

  private SQLCommon() {}

  private static final String DATABASE = "database";
  private static final String PREFIX_SOURCE = "server/springboot/database/";
  private static final String PREFIX_DATABASE_PATH = "technical/infrastructure/secondary/";

  public static Dependency hikariDependency() {
    return Dependency.builder().groupId("com.zaxxer").artifactId("HikariCP").build();
  }

  public static Dependency springDataJpa() {
    return Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-data-jpa").build();
  }

  public static Dependency sqlHibernateCore() {
    return Dependency.builder().groupId("org.hibernate").artifactId("hibernate-core").build();
  }

  public static Map<String, Object> springProperties() {
    TreeMap<String, Object> result = new TreeMap<>();
    result.put("spring.datasource.type", "com.zaxxer.hikari.HikariDataSource");

    result.put("spring.datasource.password", "");
    result.put("spring.datasource.hikari.poolName", "Hikari");
    result.put("spring.datasource.hikari.auto-commit", false);

    result.put("spring.data.jpa.repositories.bootstrap-mode", "deferred");
    result.put("spring.jpa.properties.hibernate.jdbc.time_zone", "UTC");
    result.put("spring.jpa.open-in-view", false);
    result.put("spring.jpa.properties.hibernate.id.new_generator_mappings", "true");
    result.put("spring.jpa.properties.hibernate.connection.provider_disables_autocommit", "true");
    result.put("spring.jpa.properties.hibernate.generate_statistics", false);

    result.put("spring.jpa.properties.hibernate.jdbc.batch_size", "25");
    result.put("spring.jpa.properties.hibernate.order_inserts", "true");
    result.put("spring.jpa.properties.hibernate.order_updates", "true");
    result.put("spring.jpa.properties.hibernate.query.fail_on_pagination_over_collection_fetch", "true");
    result.put("spring.jpa.properties.hibernate.query.in_clause_parameter_padding", "true");

    result.put("spring.jpa.hibernate.ddl-auto", "none");
    result.put("spring.jpa.hibernate.naming.physical-strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
    result.put("spring.jpa.hibernate.naming.implicit-strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
    return result;
  }

  public static Map<String, Level> loggers() {
    TreeMap<String, Level> result = new TreeMap<>();
    result.put("org.hibernate.validator", Level.WARN);
    result.put("org.hibernate", Level.WARN);
    result.put("org.hibernate.ejb.HibernatePersistence", Level.OFF);
    return result;
  }

  public static String getSource(String database) {
    Assert.notBlank(DATABASE, database);
    return PREFIX_SOURCE + database;
  }

  public static String getDatabasePath(String database) {
    Assert.notBlank(DATABASE, database);
    return PREFIX_DATABASE_PATH + database;
  }
}
