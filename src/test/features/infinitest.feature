Feature: Infinitest module

  Scenario: Add infinitest
    When I apply "infinitest-filters" module to default project without properties
    Then I should have files in "/"
      | infinitest.filters |
