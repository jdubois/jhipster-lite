package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocjwt.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocjwt.application.SpringdocJwtApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class SpringdocJwtModuleConfiguration {

  @Bean
  JHipsterModuleResource springdocJwtModule(SpringdocJwtApplicationService springdocJwt) {
    return JHipsterModuleResource
      .builder()
      .slug("springdoc-jwt")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - API Documentation", "Add JWT authentication for springdoc")
      .organization(JHipsterModuleOrganization.builder().addFeatureDependency("springdoc").addModuleDependency("spring-boot-jwt").build())
      .tags("server", "swagger", "springdoc")
      .factory(springdocJwt::buildModule);
  }
}
