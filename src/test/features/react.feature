Feature: React modules

  Scenario: Should apply react module
    When I apply modules to default project
      | init       |
      | prettier   |
      | react-core |
    Then I should have files in "src/main/webapp/app/common/primary/app"
      | App.tsx |
