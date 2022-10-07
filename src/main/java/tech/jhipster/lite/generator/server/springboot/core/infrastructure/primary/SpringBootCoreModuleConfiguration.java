package tech.jhipster.lite.generator.server.springboot.core.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class SpringBootCoreModuleConfiguration {

  @Bean
  JHipsterModuleResource springBootCoreModule(SpringBootApplicationService springBoot) {
    return JHipsterModuleResource
      .builder()
      .slug(SPRING_BOOT)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
      .apiDoc("Spring Boot", "Init Spring Boot project with dependencies, App, and properties")
      .organization(JHipsterModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).addDependency(JAVA_BASE).build())
      .tags("server", "spring", "spring-boot")
      .factory(springBoot::buildModule);
  }
}
