Feature: Modules exposition

  Scenario: Should get modules list
    When I get modules list
    Then I should have category "Spring Boot - Component Tests" with module
      | Slug        | springboot-cucumber                 |
      | Description | Add cucumber integration to project |