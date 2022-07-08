Feature: Springdoc modules

  Scenario: Should add Springdoc for MVC
    When I apply legacy modules to default project
      | /api/build-tools/maven                     |
      | /api/servers/spring-boot                   |
      | /api/servers/spring-boot/api-documentations/springdoc/init |
    Then I should have files in ""
      | pom.xml |
    And I should have "springdoc-openapi-ui" in "pom.xml"
    And I should have history entry for "springdoc-openapi"
    And I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/springdoc"
      | SpringdocConfiguration.java |
    And I should not have "jwt" in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java"
    And I should have files in "src/main/resources/config"
      | application.properties |
    And I should have files in "src/test/resources/config"
      | application.properties |

  Scenario: Should add Springdoc for Webflux
    When I apply legacy modules to default project
      | /api/build-tools/maven                     |
      | /api/servers/spring-boot                   |
      | /api/servers/spring-boot/reactive-servers/netty |
      | /api/servers/spring-boot/api-documentations/springdoc/init |
    Then I should have files in ""
      | pom.xml |
    And I should have "springdoc-openapi-webflux-ui" in "pom.xml"
    And I should have history entry for "springdoc-openapi"
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
    And I should have history entry for "springdoc-openapi"
    And I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/springdoc"
      | SpringdocConfiguration.java |
    And I should have files in "src/main/resources/config"
      | application.properties |

  Scenario: Should add Springdoc for MVC with JWT Security
    When I apply legacy modules to default project
      | /api/build-tools/maven                     |
      | /api/servers/spring-boot                   |
      | /api/servers/spring-boot/api-documentations/springdoc/init-with-security-jwt |
    Then I should have files in ""
      | pom.xml |
    And I should have "springdoc-openapi-ui" in "pom.xml"
    And I should have history entry for "springdoc-openapi-with-security-jwt"
    And I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/springdoc"
      | SpringdocConfiguration.java |
    And I should have "jwt" in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java"
    And I should have files in "src/main/resources/config"
      | application.properties |
    And I should have files in "src/test/resources/config"
      | application.properties |

  Scenario: Should add Springdoc for Webflux with JWT Security
    When I apply legacy modules to default project
      | /api/build-tools/maven                     |
      | /api/servers/spring-boot                   |
      | /api/servers/spring-boot/reactive-servers/netty |
      | /api/servers/spring-boot/api-documentations/springdoc/init-with-security-jwt |
    Then I should have files in ""
      | pom.xml |
    And I should have "springdoc-openapi-webflux-ui" in "pom.xml"
    And I should have history entry for "springdoc-openapi-with-security-jwt"
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
    And I should have history entry for "springdoc-openapi-with-security-jwt"
    And I should have files in "src/main/java/tech/jhipster/chips/technical/infrastructure/primary/springdoc"
      | SpringdocConfiguration.java |
    And I should have files in "src/main/resources/config"
      | application.properties |
