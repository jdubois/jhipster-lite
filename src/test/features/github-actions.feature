Feature: GitHub action module 

  Scenario: Should apply github action module
    When I apply "github-actions" module to default project without properties
    Then I should have files in ".github/workflows/"
      | github-actions.yml |
