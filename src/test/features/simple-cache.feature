Feature: Simple cache module

  Scenario: Should apply simple cache module
    When I apply "springboot-cache" module to default project with maven file
      | packageName | tech.jhipster.chips |
    Then I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/secondary/cache"
      | CacheConfiguration.java |
