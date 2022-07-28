Feature: Spring Boot Actuator modules

  Scenario: Should add Spring Boot Actuator
    When I apply modules to default project
      | maven-java          |
      | springboot          |
      | springboot-actuator |
    Then I should have entries in "src/main/resources/config/application.properties"
      | management.endpoints.web.base-path                    |
      | management.endpoints.web.exposure.include             |
      | management.endpoint.health.probes.enabled             |
      | spring.security.oauth2.client.registration.oidc.scope |
      | management.endpoint.health.show-details               |

