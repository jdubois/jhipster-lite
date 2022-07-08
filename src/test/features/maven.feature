Feature: Maven module

  Scenario: Should apply maven init module
    When I apply "maven-java" module to default project
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
      | projectNmae | Jhipster            |
    Then I should have files in "."
      | pom.xml |
