Feature: Angular jwt module

  Scenario: Should apply Angular jwt module    
    When I apply modules to default project
      | init         |
      | angular-core |
      | angular-jwt  |
    Then I should have files in "src/main/webapp/app/auth"
      | auth-jwt.service.spec.ts |
