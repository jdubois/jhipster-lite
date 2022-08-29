package tech.jhipster.lite.generator.client.angular.security.oauth2.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.angular.security.oauth2.application.AngularOauth2ApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class AngularOAuth2ModuleConfiguration {

  @Bean
  JHipsterModuleResource angularOAuth2Module(AngularOauth2ApplicationService angularOAuth2) {
    return JHipsterModuleResource
      .builder()
      .slug("angular-oauth2")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Angular", "Add OAuth2 authentication"))
      .organization(JHipsterModuleOrganization.builder().feature("angular-authentication").addModuleDependency("angular-core").build())
      .tags("client", "angular")
      .factory(angularOAuth2::buildModule);
  }
}
