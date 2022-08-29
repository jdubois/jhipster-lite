Feature: Svelte

  Scenario: Should apply Svelte module
    When I apply modules to default project
      | init     |
      | prettier |
      | svelte   |
    Then I should have files in ""
      | tsconfig.json |
