Feature: Cucumber module

  Scenario: Should get cucumber module properties definition
    When I get module "spring-boot-cucumber" properties definition
    Then I should have properties definitions
      | Key         | Type    | Mandatory |
      | packageName | STRING  | true      |
      | baseName    | STRING  | true      |
      | indentSize  | INTEGER | false     |

  Scenario: Should add cucumber elements using module url
    When I apply "spring-boot-cucumber" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in "src/test/java/tech/jhipster/chips/cucumber"
      | CucumberConfiguration.java |

  Scenario: Should apply cucumber JPA reset module
    When I apply modules to default project
      | maven-java                    |
      | spring-boot-cucumber           |
      | spring-boot-cucumber-jpa-reset |
    Then I should have files in "src/test/java/tech/jhipster/chips/cucumber"
      | CucumberJpaReset.java |
