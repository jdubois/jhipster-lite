Feature: Vue.js modules

  Scenario: Should apply vue core module
    When I apply "vue" module to default project with package json without properties
    Then I should have files in "src/main/webapp/app/http"
      | AxiosHttp.ts |
    And I should have history entry for "vue"
    
  Scenario: Should apply pinia module
    Given I apply "vue" module to default project with package json without properties
    When I apply "vue-pinia" module without properties to last project
    Then I should have "import piniaPersist from 'pinia-plugin-persist'" in "src/main/webapp/app/main.ts"
    And I should have history entry for "vue-pinia"