package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocoauth.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocoauth.application.SpringdocOauth2ApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class SpringdocOauth2ModuleConfiguration {

  @Bean
  JHipsterModuleResource springDocOAuth2Module(SpringdocOauth2ApplicationService springdocOauth2) {
    return JHipsterModuleResource
      .builder()
      .slug(SPRINGDOC_OAUTH_2)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - API Documentation", "Add OAuth2 authentication for springdoc")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRINGDOC).addDependency(SPRING_BOOT_OAUTH_2).build())
      .tags("server", "swagger", "springdoc")
      .factory(springdocOauth2::buildModule);
  }
}
