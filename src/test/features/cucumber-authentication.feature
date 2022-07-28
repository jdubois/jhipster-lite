Feature: Cucumber authentication

  Scenario: Should apply cucumber oauth2 authentication module
    When I apply modules to default project
      | maven-java                     |
      | springboot-cucumber            |
      | cucumber-oauth2-authentication |
    Then I should have files in "src/test/java/tech/jhipster/chips/authentication/infrastructure/primary"
      | AuthenticationSteps.java |

  Scenario: Should apply cucumber jwt authentication module
    When I apply modules to default project
      | maven-java                  |
      | springboot-cucumber         |
      | cucumber-jwt-authentication |
    Then I should have files in "src/test/java/tech/jhipster/chips/authentication/infrastructure/primary"
      | AuthenticationSteps.java |
