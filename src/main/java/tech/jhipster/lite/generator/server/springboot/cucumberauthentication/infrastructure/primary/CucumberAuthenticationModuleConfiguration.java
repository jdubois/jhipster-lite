package tech.jhipster.lite.generator.server.springboot.cucumberauthentication.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.cucumberauthentication.application.CucumberAuthenticationApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class CucumberAuthenticationModuleConfiguration {

  @Bean
  JHipsterModuleResource cucumberOAuth2AuthenticationModule(CucumberAuthenticationApplicationService cucumberAuthentications) {
    return JHipsterModuleResource
      .builder()
      .slug("spring-boot-cucumber-oauth2-authentication")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
      .apiDoc("Spring Boot - Component Tests", "Add OAuth2 authentication steps for cucumber")
      .organization(
        JHipsterModuleOrganization
          .builder()
          .feature("cucumber-authentication")
          .addModuleDependency("spring-boot-cucumber")
          .addModuleDependency("spring-boot-oauth2")
          .build()
      )
      .tags("server", "spring", "spring-boot", "test", "oauth2")
      .factory(cucumberAuthentications::buildOauth2Module);
  }

  @Bean
  JHipsterModuleResource cucumberJwtAuthenticationModule(CucumberAuthenticationApplicationService cucumberAuthentications) {
    return JHipsterModuleResource
      .builder()
      .slug("spring-boot-cucumber-jwt-authentication")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Spring Boot - Component Tests", "Add JWT authentication steps for cucumber")
      .organization(
        JHipsterModuleOrganization
          .builder()
          .feature("cucumber-authentication")
          .addModuleDependency("spring-boot-cucumber")
          .addModuleDependency("spring-boot-jwt")
          .build()
      )
      .tags("server", "spring", "spring-boot", "test", "jwt")
      .factory(cucumberAuthentications::buildJWTModule);
  }
}
