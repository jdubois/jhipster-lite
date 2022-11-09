Feature: PostgreSQL Dialect module

  Scenario: Should add PostgreSQL Dialect
    When I apply modules to default project
      | maven-java |
      | spring-boot |
      | postgresql |
      | postgresql-dialect |
    And I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/secondary/postgresql"
      | FixedPostgreSQL10Dialect.java |
    And I should have files in "src/test/java/tech/jhipster/chips/technical/infrastructure/secondary/postgresql"
      | FixedPostgreSQL10DialectTest.java |
    And I should have files in "src/main/resources/config"
      | application.properties |
    And I should have files in "src/test/resources/config"
      | application.properties |
