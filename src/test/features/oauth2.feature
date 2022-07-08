Feature: OAuth2 modules

  Scenario: Should add OAuth2
    When I apply legacy modules to default project
      | /api/build-tools/maven                           |
      | /api/servers/spring-boot                         |
      | /api/servers/spring-boot/security-systems/oauth2 |
    Then I should have files in "src/main/java/tech/jhipster/chips/authentication/domain"
      | Role.java |

  Scenario: Should add OAuth2 account
    When I apply legacy modules to default project
      | /api/build-tools/maven                                    |
      | /api/servers/spring-boot                                  |
      | /api/servers/spring-boot/security-systems/oauth2          |
      | /api/servers/spring-boot/security-systems/oauth2/account |
    Then I should have files in "src/main/java/tech/jhipster/chips/account/domain"
      | Account.java |
