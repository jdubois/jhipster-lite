package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.application.OAuth2SecurityApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class OAuth2ModuleConfiguration {

  private static final String AUTHENTICATION = "authentication";

  @Bean
  JHipsterModuleResource oAuth2Module(OAuth2SecurityApplicationService oAuth2) {
    return JHipsterModuleResource
      .builder()
      .slug("spring-boot-oauth2")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
      .apiDoc(
        "Spring Boot - MVC - Security",
        "Add a Spring Security: OAuth 2.0 / OIDC Authentication (stateful, works with Keycloak and Okta)"
      )
      .organization(
        JHipsterModuleOrganization
          .builder()
          .feature(AUTHENTICATION)
          .addModuleDependency("java-base")
          .addFeatureDependency("web-error-management")
          .addFeatureDependency("spring-server")
          .build()
      )
      .tags("server", "spring", "spring-boot", AUTHENTICATION)
      .factory(oAuth2::buildOAuth2Module);
  }

  @Bean
  JHipsterModuleResource oAuth2AccountModule(OAuth2SecurityApplicationService oAuth2) {
    return JHipsterModuleResource
      .builder()
      .slug("spring-boot-oauth2-account")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Spring Boot - MVC - Security", "Add a account context for OAuth 2.0 / OIDC Authentication")
      .organization(JHipsterModuleOrganization.builder().addModuleDependency("spring-boot-oauth2").build())
      .tags("server", "spring", "spring-boot", AUTHENTICATION, "account", "user")
      .factory(oAuth2::buildOAuth2AccountModule);
  }
}
