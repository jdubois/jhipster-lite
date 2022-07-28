Feature: Zalado problems

  Scenario: Should apply zalando problems module
    When I apply "zalando-problems" module to default project with maven file
      | packageName | tech.jhipster.chips |
    Then I should have "<artifactId>problem-spring-web</artifactId>" in "pom.xml"
