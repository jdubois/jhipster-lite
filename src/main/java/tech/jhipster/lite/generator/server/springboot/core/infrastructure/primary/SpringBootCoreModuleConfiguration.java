package tech.jhipster.lite.generator.server.springboot.core.infrastructure.primary;

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
      .slug("spring-boot")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
      .apiDoc("Spring Boot", "Init Spring Boot project with dependencies, App, and properties")
      .organization(JHipsterModuleOrganization.builder().addFeatureDependency("java-build-tool").addModuleDependency("java-base").build())
      .tags("server", "spring", "spring-boot")
      .factory(springBoot::buildModule);
  }
}
