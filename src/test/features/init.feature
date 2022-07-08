Feature: Init

  Scenario: Should init from legacy url
    When I apply legacy module "/api/inits/full" to default project
    Then I should have files in ""
      | .gitignore                    |
      | .gitattributes                |
      | .editorconfig                 |
      | .eslintignore                 |
      | .lintstagedrc.js              |
      | .prettierignore               |
      | .prettierrc                   |
      | package.json                  |
      | README.md                     |
    And I should have files in ".husky"
      | pre-commit                    |
    And I should have files in ".git"
      | config                        |
      | HEAD                          |
      
  Scenario: Should init from module
    When I apply "init" module to default project
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
      | endOfLine   | lf                  |
    Then I should have files in ""
      | .gitignore                    |
      | .gitattributes                |
      | .editorconfig                 |
      | .eslintignore                 |
      | .lintstagedrc.js              |
      | .prettierignore               |
      | .prettierrc                   |
      | package.json                  |
      | README.md                     |
    And I should have files in ".husky"
      | pre-commit                    |
    And I should have files in ".git"
      | config                        |
      | HEAD                          |

  Scenario: Should init minimal from legacy url
    When I apply legacy module "/api/inits/minimal" to default project
    Then I should have files in ""
      | .gitignore                    |
      | .gitattributes                |
      | .editorconfig                 |
      | .eslintignore                 |
      | README.md                     |
    And I should have files in ".git"
      | config                        |
      | HEAD                          |
    And I should not have files in ""
      | .lintstagedrc.js              |
      | .prettierignore               |
      | .prettierrc                   |
      | package.json                  |
    And I should not have files in ".husky"
      | pre-commit                    |
      
  Scenario: Should init minimal from module
    When I apply "init-minimal" module to default project
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
      | endOfLine   | lf                  |
    Then I should have files in ""
      | .gitignore                    |
      | .gitattributes                |
      | .editorconfig                 |
      | .eslintignore                 |
      | README.md                     |
    And I should have files in ".git"
      | config                        |
      | HEAD                          |
    And I should not have files in ""
      | .lintstagedrc.js              |
      | .prettierignore               |
      | .prettierrc                   |
      | package.json                  |
    And I should not have files in ".husky"
      | pre-commit                    |
