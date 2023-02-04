Feature: Cassandra module

  Scenario: Should apply cassandra module
    When I apply modules to default project
      | maven-java          |
      | cassandra           |
    Then I should have files in "documentation"
      | cassandra.md |
