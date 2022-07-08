Feature: DevTools module

  Scenario: Should add devtools elements using legacy url
    When I apply legacy module "/api/servers/spring-boot/technical-tools/devtools" to default project with maven file
    Then I should have files in "documentation"
      | dev-tools.md |

  Scenario: Should get devtools module properties definition
    When I get module "springboot-devtools" properties definition
    Then I should have properties definitions
      | Key                   | Type    | Mandatory |
      | packageName           | STRING  | true      |
      | baseName              | STRING  | true      |
      | prettierDefaultIndent | INTEGER | false     |

  Scenario: Should add devtools elements using module url
    When I apply "springboot-devtools" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in "documentation"
      | dev-tools.md |
    And I should have history entry for "springboot-devtools"
