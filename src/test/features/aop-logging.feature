Feature: AOP logging

  Scenario: Should apply AOP logging module
    When I apply "aop-logging" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/secondary/aop/logging"
      | LoggingAspectConfiguration.java |
