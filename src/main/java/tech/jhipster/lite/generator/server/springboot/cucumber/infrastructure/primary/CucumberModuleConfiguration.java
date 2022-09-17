package tech.jhipster.lite.generator.server.springboot.cucumber.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.cucumber.application.CucumberApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class CucumberModuleConfiguration {

  @Bean
  JHipsterModuleResource cucumberInitializationModule(CucumberApplicationService cucumber) {
    return JHipsterModuleResource
      .builder()
      .slug("spring-boot-cucumber")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc("Spring Boot - Component Tests", "Add cucumber integration to project")
      .organization(JHipsterModuleOrganization.builder().addFeatureDependency("spring-server").build())
      .tags("server", "spring", "spring-boot", "test")
      .factory(cucumber::buildInitializationModule);
  }

  @Bean
  JHipsterModuleResource cucumberJpaResetModule(CucumberApplicationService cucumber) {
    return JHipsterModuleResource
      .builder()
      .slug("spring-boot-cucumber-jpa-reset")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Spring Boot - Component Tests", "Add jpa reset for cucumber")
      .organization(
        JHipsterModuleOrganization.builder().addModuleDependency("spring-boot-cucumber").addFeatureDependency("jpa-persistence").build()
      )
      .tags("server", "spring", "spring-boot", "test")
      .factory(cucumber::buildJpaResetModule);
  }
}
