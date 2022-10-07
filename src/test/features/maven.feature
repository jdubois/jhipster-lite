Feature: Maven module

  Scenario: Should apply maven init module
    When I apply "maven-java" module to default project
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
      | projectNmae | Jhipster            |
    Then I should have files in "."
      | pom.xml |

  Scenario: Should apply maven wrapper module
    When I apply "maven-wrapper" module to default project without parameters
    Then I should have files in ".mvn/wrapper"
      | maven-wrapper.jar |
      | maven-wrapper.properties |
    Then I should have files in ""
      | mvnw.cmd |
      | mvnw |
