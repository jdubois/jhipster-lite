Feature: Bean validation test

  Scenario: Should apply bean validation test module
    When I apply "bean-validation-test" module to default project with maven file
      | packageName | tech.jhipster.chips |
    Then I should have files in "src/test/java/tech/jhipster/chips"
      | BeanValidationAssertions.java |
