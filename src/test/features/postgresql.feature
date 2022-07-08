Feature: postgreSQL module

  Scenario: Should add postgreSQL elements using legacy url
    When I apply legacy modules to default project
      | /api/build-tools/maven                     |
      | /api/servers/spring-boot                   |
      | /api/servers/spring-boot/databases/postgresql |
    Then I should have files in ""
      | pom.xml |
    And I should have history entry for "postgresql"
    And I should have files in "documentation"
      | postgresql.md |
    And I should have files in "src/main/docker"
      | postgresql.yml |
    And I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/secondary/postgresql"
      | DatabaseConfiguration.java |
      | FixedPostgreSQL10Dialect.java |
    And I should have files in "src/test/java/tech/jhipster/chips/technical/infrastructure/secondary/postgresql"
      | FixedPostgreSQL10DialectTest.java |
    And I should have files in "src/main/resources/config"
      | application.properties |
    And I should have files in "src/test/resources/config"
      | application.properties |

  Scenario: Should get postgreSQL module properties definition
    When I get module "postgresql" properties definition
    Then I should have properties definitions
      | Key                   | Type    | Mandatory |
      | packageName           | STRING  | true      |
      | baseName              | STRING  | true      |
      | prettierDefaultIndent | INTEGER | false     |

  Scenario: Should add postgreSQL elements using module url
    When I apply "postgresql" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in ""
      | pom.xml |
    And I should have history entry for "postgresql"
    And I should have files in "documentation"
      | postgresql.md |
    And I should have files in "src/main/docker"
      | postgresql.yml |
    And I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/secondary/postgresql"
      | DatabaseConfiguration.java |
      | FixedPostgreSQL10Dialect.java |
    And I should have files in "src/test/java/tech/jhipster/chips/technical/infrastructure/secondary/postgresql"
      | FixedPostgreSQL10DialectTest.java |
    And I should have files in "src/main/resources/config"
      | application.properties |
    And I should have files in "src/test/resources/config"
      | application.properties |
