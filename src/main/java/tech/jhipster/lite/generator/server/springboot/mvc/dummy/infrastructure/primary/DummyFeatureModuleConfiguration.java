package tech.jhipster.lite.generator.server.springboot.mvc.dummy.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.dummy.application.DummyApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class DummyFeatureModuleConfiguration {

  @Bean
  JHipsterModuleResource dummyFeatureModule(DummyApplicationService dummy) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/features/dummy")
      .slug("dummy-feature")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - MVC", "Add Dummy context with some APIs"))
      .tags("server")
      .factory(dummy::buildModule);
  }
}
