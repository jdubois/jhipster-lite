package tech.jhipster.lite.generator.client.angular.security.jwt.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.angular.security.jwt.application.AngularJwtApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class AngularJwtModuleConfiguration {

  @Bean
  JHipsterModuleResource angularJwtModule(AngularJwtApplicationService angularJwt) {
    return JHipsterModuleResource
      .builder()
      .slug("angular-jwt")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Angular", "Add Angular with authentication JWT"))
      .organization(JHipsterModuleOrganization.builder().feature("angular-authentication").addModuleDependency("angular-core").build())
      .tags("client", "angular")
      .factory(angularJwt::buildModule);
  }
}
