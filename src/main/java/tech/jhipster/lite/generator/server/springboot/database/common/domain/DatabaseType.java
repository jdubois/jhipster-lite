package tech.jhipster.lite.generator.server.springboot.database.common.domain;

public enum DatabaseType {
  POSTGRESQL("postgresql"),
  MYSQL("mysql"),
  MARIADB("mariadb"),
  MSSQL("mssql");

  private final String id;

  DatabaseType(String id) {
    this.id = id;
  }

  public String id() {
    return id;
  }
}
