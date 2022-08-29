Feature: Angular oauth2 module

  Scenario: Should apply Angular OAuth2 module    
    When I apply modules to default project
      | init           |
      | angular-core   |
      | angular-oauth2 |
    Then I should have files in "src/main/webapp/app/auth"
      | oauth2-auth.service.ts |
