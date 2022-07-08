Feature: Gitpod

  Scenario: Should apply gitpod module
    When I apply "gitpod" module to default project without properties
    Then I should have files in "."
      | .gitpod.yml |
