Feature: GitLab CI module

  Scenario: Should apply gitlab ci module
    When I apply "gitlab-ci" module to default project without parameters
    Then I should have files in "."
      | .gitlab-ci.yml |
