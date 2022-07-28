Feature: Modules

  Scenario: Should apply and commit module
    When I apply and commit "init" module to default project
      | projectName | Test project |
    Then I should have commit "Apply init module"
    
  Scenario: Should apply module without commit
    When I apply "init" module to default project
      | projectName | Test project |
    Then I should not have any commit

  Scenario: Should get modules list
    When I get modules list
    Then I should have category "Spring Boot - Component Tests" with module
      | Slug        | springboot-cucumber                 |
      | Description | Add cucumber integration to project |
