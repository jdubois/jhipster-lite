Feature: Angular health module

  Scenario: Should apply Angular health module    
    When I apply modules to default project
      | init           |
      | angular-core   |
      | angular-health |
    Then I should have files in "src/main/webapp/app/admin/health/modal"
      | health-modal.component.css |
