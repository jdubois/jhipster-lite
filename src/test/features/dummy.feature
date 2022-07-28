Feature: Dummy feature module

  Scenario: Should apply dummy feature module
    When I apply "dummy-feature" module to default project with maven file
      | packageName | tech.jhipster.chips |
    Then I should have files in "src/main/java/tech/jhipster/chips/dummy/domain"
      | Amount.java |
