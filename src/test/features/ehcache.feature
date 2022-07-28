Feature: EHCache modules

  Scenario: Should apply Java EHCache module
    When I apply "ehcache-with-java-config" module to default project with maven file
      | packageName | tech.jhipster.chips |
    Then I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/secondary/cache"
      | EhcacheProperties.java |

  Scenario: Should apply XML EHCache module
    When I apply "ehcache-with-xml-config" module to default project with maven file
      | packageName | tech.jhipster.chips |
    Then I should have files in "src/main/resources/config/ehcache/"
      | ehcache.xml |
