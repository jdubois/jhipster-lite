Feature: Pulsar module

  Scenario: Should apply pulsar module
    When I apply modules to default project
      | maven-java        |
      | springboot        |
      | springboot-pulsar |
    Then I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/config/pulsar"
      | PulsarProperties.java |
