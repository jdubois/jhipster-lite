Feature: Typescript

  Scenario: Should apply Typescript module
    When I apply "typescript" module to default project with package json without properties
    Then I should have files in ""
      | tsconfig.json |
