Feature: Java base

  Scenario: Should add java base
    When I apply legacy module "/api/servers/java/base" to default project
    Then I should have files in "src/main/java/tech/jhipster/chips/error/domain"
      | Assert.java |
    And I should have history entry for "java-base"

  Scenario: Should get java base properties definition
    When I get module "java-base" properties definition
    Then I should have properties definitions
      | Key                   | Type    | Mandatory |
      | packageName           | STRING  | true      |
      | baseName              | STRING  | true      |

  Scenario: Should add java base elements using module url
    When I apply "java-base" module to default project
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in "src/main/java/tech/jhipster/chips/error/domain"
      | Assert.java |
    And I should have history entry for "java-base"