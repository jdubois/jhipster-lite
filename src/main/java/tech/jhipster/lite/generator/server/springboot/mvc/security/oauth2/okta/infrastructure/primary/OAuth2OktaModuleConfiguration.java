package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.okta.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;
import static tech.jhipster.lite.module.domain.resource.JHipsterModulePropertyDefinition.mandatoryStringProperty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.okta.application.OAuth2OktaSecurityApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertyDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class OAuth2OktaModuleConfiguration {

  private static final String AUTHENTICATION = "authentication";

  @Bean
  JHipsterModuleResource oAuth2OktaModule(OAuth2OktaSecurityApplicationService oAuth2) {
    return JHipsterModuleResource
      .builder()
      .slug(SPRING_BOOT_OAUTH_2_OKTA)
      .propertiesDefinition(initPropertiesDefinition())
      .apiDoc(
        "Spring Boot - MVC - Security",
        "Add a Spring Security: OAuth 2.0 / OIDC Authentication / Okta Provider (stateful, works with Keycloak and Okta)"
      )
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_BOOT_OAUTH_2).build())
      .tags("server", "spring", "spring-boot", AUTHENTICATION, "okta")
      .factory(oAuth2::buildOAuth2OktaModule);
  }

  private JHipsterModulePropertiesDefinition initPropertiesDefinition() {
    return JHipsterModulePropertiesDefinition
      .builder()
      .addProjectBaseName()
      .addProjectName()
      .add(oktaDomain())
      .add(oktaClientId())
      .addIndentation()
      .build();
  }

  public static JHipsterModulePropertyDefinition oktaDomain() {
    return mandatoryStringProperty("oktaDomain").description("Okta domain").defaultValue("dev-123456.okta.com").order(600).build();
  }

  public static JHipsterModulePropertyDefinition oktaClientId() {
    return mandatoryStringProperty("oktaClientId")
      .description("Okta Client ID for OIDC application")
      .defaultValue("0oab8eb55Kb9jdMIr5d6")
      .order(700)
      .build();
  }
}
