package tech.jhipster.lite.generator.server.javatool.base.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.javatool.base.application.JavaBaseApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class JavaBaseModuleConfiguration {

  @Bean
  JHipsterModuleResource javaBaseModule(JavaBaseApplicationService javaBase) {
    return JHipsterModuleResource
      .builder()
      .slug("java-base")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc(new JHipsterModuleApiDoc("Java", "Add Base classes and Error domain to project"))
      .organization(JHipsterModuleOrganization.builder().addFeatureDependency("java-build-tool").build())
      .tags("server")
      .factory(javaBase::build);
  }
}
