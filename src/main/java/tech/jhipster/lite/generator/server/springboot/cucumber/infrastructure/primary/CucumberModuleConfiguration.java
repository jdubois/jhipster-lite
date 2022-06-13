package tech.jhipster.lite.generator.server.springboot.cucumber.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.generator.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.generator.module.infrastructure.primary.JHipsterModuleResource;
import tech.jhipster.lite.generator.server.springboot.cucumber.application.CucumberApplicationService;

@Configuration
class CucumberModuleConfiguration {

  @Bean
  JHipsterModuleResource cucumberModule(CucumberApplicationService cucumber) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/component-tests/cucumber")
      .slug("springboot-cucumber")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Component Tests", "Add cucumber integration to project"))
      .factory(cucumber::build);
  }
}
