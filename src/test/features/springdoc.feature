Feature: Springdoc modules

  Scenario: Should add Springdoc for MVC
    When I apply modules to default project
      | maven-java        |
      | springboot        |
      | springdoc-openapi |
    Then I should have files in ""
      | pom.xml |
    And I should have "springdoc-openapi-ui" in "pom.xml"
    And I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/springdoc"
      | SpringdocConfiguration.java |
    And I should not have "jwt" in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java"
    And I should have files in "src/main/resources/config"
      | application.properties |
    And I should have files in "src/test/resources/config"
      | application.properties |

  Scenario: Should add Springdoc for Webflux
    When I apply modules to default project
      | maven-java               |
      | springboot               |
      | springboot-webflux-netty |
      | springdoc-openapi        |
    Then I should have files in ""
      | pom.xml |
    And I should have "springdoc-openapi-webflux-ui" in "pom.xml"
    And I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/springdoc"
      | SpringdocConfiguration.java |
    And I should not have "jwt" in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java"
    And I should have files in "src/main/resources/config"
      | application.properties |
    And I should have files in "src/test/resources/config"
      | application.properties |

  Scenario: Should get Springdoc module properties definition
    When I get module "springdoc-openapi" properties definition
    Then I should have properties definitions
      | Key                   | Type    | Mandatory |
      | packageName           | STRING  | true      |
      | baseName              | STRING  | true      |
      | prettierDefaultIndent | INTEGER | false     |

  Scenario: Should add Springdoc elements using module url
    When I apply "springdoc-openapi" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in ""
      | pom.xml |
    And I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/springdoc"
      | SpringdocConfiguration.java |
    And I should have files in "src/main/resources/config"
      | application.properties |

  Scenario: Should add Springdoc for MVC with JWT Security
    When I apply modules to default project
      | maven-java                          |
      | springboot                          |
      | springdoc-openapi-with-security-jwt |
    Then I should have files in ""
      | pom.xml |
    And I should have "springdoc-openapi-ui" in "pom.xml"
    And I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/springdoc"
      | SpringdocConfiguration.java |
    And I should have "jwt" in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java"
    And I should have files in "src/main/resources/config"
      | application.properties |
    And I should have files in "src/test/resources/config"
      | application.properties |

  Scenario: Should add Springdoc for MVC with OAuth2 Security
    When I apply modules to default project
      | maven-java                             |
      | springboot                             |
      | springboot-oauth2                      |
      | springdoc-openapi-with-security-oauth2 |
    Then I should have files in ""
      | pom.xml |
    And I should have "springdoc-openapi-ui" in "pom.xml"
    And I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/springdoc"
      | SpringdocConfiguration.java |
    And I should have "OAUTH2" in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java"
    And I should have files in "src/main/resources/config"
      | application.properties |
    And I should have files in "src/test/resources/config"
      | application.properties |

  Scenario: Should add Springdoc for Webflux with JWT Security
    When I apply modules to default project
      | maven-java                          |
      | springboot                          |
      | springboot-webflux-netty            |
      | springdoc-openapi-with-security-jwt |
    Then I should have files in ""
      | pom.xml |
    And I should have "springdoc-openapi-webflux-ui" in "pom.xml"
    And I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/springdoc"
      | SpringdocConfiguration.java |
    And I should have "jwt" in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java"
    And I should have files in "src/main/resources/config"
      | application.properties |
    And I should have files in "src/test/resources/config"
      | application.properties |

  Scenario: Should get Springdoc with jwt security module properties definition
    When I get module "springdoc-openapi-with-security-jwt" properties definition
    Then I should have properties definitions
      | Key                   | Type    | Mandatory |
      | packageName           | STRING  | true      |
      | baseName              | STRING  | true      |
      | prettierDefaultIndent | INTEGER | false     |

  Scenario: Should add Springdoc with JWT Security elements using module url
    When I apply "springdoc-openapi-with-security-jwt" module to default project with maven file
      | packageName | tech.jhipster.chips |
      | baseName    | jhipster            |
    Then I should have files in ""
      | pom.xml |
    And I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/springdoc"
      | SpringdocConfiguration.java |
    And I should have files in "src/main/resources/config"
      | application.properties |
