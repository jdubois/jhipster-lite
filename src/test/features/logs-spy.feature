Feature: Logs spy

  Scenario: Should apply Logs Spy
    When I apply "logs-spy" module to default project
      | packageName | tech.jhipster.chips |
    Then I should have files in "src/test/java/tech/jhipster/chips"
      | LogsSpy.java |