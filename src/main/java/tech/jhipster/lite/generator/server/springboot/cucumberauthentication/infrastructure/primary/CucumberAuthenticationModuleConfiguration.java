package tech.jhipster.lite.generator.server.springboot.cucumberauthentication.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.cucumberauthentication.application.CucumberAuthenticationApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class CucumberAuthenticationModuleConfiguration {

  @Bean
  JHipsterModuleResource cucumberOAuth2AuthenticationModule(CucumberAuthenticationApplicationService cucumberAuthentications) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/component-tests/cucumber-oauth2-authentication")
      .slug("cucumber-oauth2-authentication")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Component Tests", "Add oauth2 authentication steps for cucumber"))
      .tags("server", "spring", "spring-boot", "test", "oauth2")
      .factory(cucumberAuthentications::buildOauth2Module);
  }

  @Bean
  JHipsterModuleResource cucumberJwtAuthenticationModule(CucumberAuthenticationApplicationService cucumberAuthentications) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/component-tests/cucumber-jwt-authentication")
      .slug("cucumber-jwt-authentication")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Component Tests", "Add jwt authentication steps for cucumber"))
      .tags("server", "spring", "spring-boot", "test", "jwt")
      .factory(cucumberAuthentications::buildJWTModule);
  }
}
