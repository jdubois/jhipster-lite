Feature: Flyway module

  Scenario: Should apply flyway module without mysql dependency
    When I apply "flyway" module to default project with maven file
      | packageName | tech.jhipster.chips |
    Then I should have "<artifactId>flyway-core</artifactId>" in "pom.xml"
    And I should not have "<artifactId>flyway-mysql</artifactId>" in "pom.xml"

  Scenario: Should apply flyway module with mysql dependency
    When I apply "flyway" module to default project with maven file
      | packageName    | tech.jhipster.chips |
      | addFlywayMysql | true                |
    Then I should have "<artifactId>flyway-core</artifactId>" in "pom.xml"
    And I should have "<artifactId>flyway-mysql</artifactId>" in "pom.xml"
