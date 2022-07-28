Feature: MongoDB module

  Scenario: Should apply mongo module
    When I apply modules to default project
      | maven-java |
      | springboot |
      | mongodb    |
    Then I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/secondary/mongodb"
      | MongodbDatabaseConfiguration.java |
