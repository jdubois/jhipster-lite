Feature: React modules

  Scenario: Should apply not styled react module
     When I apply "react" module to default project with package json
      | baseName    | jhipster            |
    Then I should have files in "src/main/webapp/app/common/primary/app"
      | App.tsx |

   Scenario: Should apply styled react module
     When I apply "react-styled" module to default project with package json
      | baseName    | jhipster            |
    Then I should have files in "src/main/webapp/app/common/primary/app"
      | App.tsx |
      