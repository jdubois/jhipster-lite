package tech.jhipster.lite.generator.client.angular.security.jwt.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.angular.security.jwt.application.AngularJwtApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class AngularJwtModuleConfiguration {

  @Bean
  JHipsterModuleResource angularJwtModule(AngularJwtApplicationService angularJwt) {
    return JHipsterModuleResource
      .builder()
      .slug(ANGULAR_JWT)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build())
      .apiDoc("Angular", "Add Angular with authentication JWT")
      .organization(JHipsterModuleOrganization.builder().feature(ANGULAR_AUTHENTICATION).addDependency(ANGULAR_CORE).build())
      .tags("client", "angular")
      .factory(angularJwt::buildModule);
  }
}
