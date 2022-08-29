Feature: OAuth2 modules

  Scenario: Should add OAuth2
    When I apply modules to default project
      | maven-java       |
      | spring-boot       |
      |spring-boot-oauth2 |
    Then I should have files in "src/test/java/tech/jhipster/chips/authentication/infrastructure/primary"
      | ClaimsTest.java |

  Scenario: Should add OAuth2 account
    When I apply modules to default project
      | maven-java                |
      | spring-boot                |
      |spring-boot-oauth2          |
      | spring-boot-oauth2-account |
    Then I should have files in "src/main/java/tech/jhipster/chips/account/domain"
      | Account.java |
