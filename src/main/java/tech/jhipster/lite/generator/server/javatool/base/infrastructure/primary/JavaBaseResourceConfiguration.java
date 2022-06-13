package tech.jhipster.lite.generator.server.javatool.base.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.generator.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.generator.module.infrastructure.primary.JHipsterModuleResource;
import tech.jhipster.lite.generator.server.javatool.base.application.JavaBaseApplicationService;

@Configuration
class JavaBaseResourceConfiguration {

  @Bean
  JHipsterModuleResource javaBaseModule(JavaBaseApplicationService javaBase) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/java/base")
      .slug("java-base")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc(new JHipsterModuleApiDoc("Java", "Add Base classes and Error domain to project"))
      .factory(javaBase::build);
  }
}
