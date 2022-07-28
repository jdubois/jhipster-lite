Feature: JWT modules

  Scenario: Should apply jwt authentication module
    When I apply modules to default project
      | maven-java     |
      | springboot     |
      | springboot-jwt |
    Then I should have files in "src/main/java/tech/jhipster/chips/authentication/infrastructure/primary"
      | JWTConfigurer.java |

      
  Scenario: Should apply basic jwt auth module
    When I apply modules to default project
      | maven-java                |
      | springboot                |
      | springboot-jwt            |
      | springboot-jwt-basic-auth |
    Then I should have files in "src/main/java/tech/jhipster/chips/account/domain"
      | AuthenticationQuery.java |
