Feature: Spring MVC

  Scenario: Should apply spring mvc tomcat module
    When I apply "springboot-tomcat" module to default project with maven file
      | packageName | tech.jhipster.chips |
    Then I should have "<artifactId>spring-boot-starter-web</artifactId>" in "pom.xml"

  Scenario: Should apply spring mvc undertow module
    When I apply "springboot-undertow" module to default project with maven file
      | packageName | tech.jhipster.chips |
    Then I should have "<artifactId>spring-boot-starter-undertow</artifactId>" in "pom.xml"
