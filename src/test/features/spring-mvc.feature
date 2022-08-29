Feature: Spring MVC

  Scenario: Should apply spring mvc tomcat module
    When I apply "spring-boot-tomcat" module to default project with maven file
      | packageName | tech.jhipster.chips |
    Then I should have "<artifactId>spring-boot-starter-web</artifactId>" in "pom.xml"

  Scenario: Should apply spring mvc undertow module
    When I apply "spring-boot-undertow" module to default project with maven file
      | packageName | tech.jhipster.chips |
    Then I should have "<artifactId>spring-boot-starter-undertow</artifactId>" in "pom.xml"
