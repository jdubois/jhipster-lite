Feature: Init

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
      | package.json                  |
      | README.md                     |
    And I should not have files in ""
      | .lintstagedrc.js              |
      | .prettierignore               |
      | .prettierrc                   |
    And I should not have files in ".husky"
      | pre-commit                    |
