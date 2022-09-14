Feature: React modules

  Scenario: Should apply react module
    When I apply "react-core" module to default project with package json
      | baseName    | jhipster            |
    Then I should have files in "src/main/webapp/app/common/primary/app"
      | App.tsx |
