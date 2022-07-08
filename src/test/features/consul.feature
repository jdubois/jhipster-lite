Feature: Consul

  Scenario: Should add consul
    When I apply legacy modules to default project
      | /api/build-tools/maven                                           |
      | /api/servers/spring-boot                                         |
      | /api/servers/spring-boot/distributed-systems/spring-cloud/consul |
    Then I should have files in "src/main/docker"
      | consul.yml |

  Scenario: Should get consul properties definition
    When I get module "consul" properties definition
    Then I should have properties definitions
      | Key                   | Type    | Mandatory |
      | baseName              | STRING  | true      |

  Scenario: Should add consul elements using module url
    When I apply "consul" module to default project with maven file
      | baseName    | jhipster            |
    Then I should have files in "src/main/docker/"
      | consul.yml |
