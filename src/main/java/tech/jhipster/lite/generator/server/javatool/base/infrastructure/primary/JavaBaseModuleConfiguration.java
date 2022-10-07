package tech.jhipster.lite.generator.server.javatool.base.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.JHLiteModuleSlug;
import tech.jhipster.lite.generator.server.javatool.base.application.JavaBaseApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class JavaBaseModuleConfiguration {

  @Bean
  JHipsterModuleResource javaBaseModule(JavaBaseApplicationService javaBase) {
    return JHipsterModuleResource
      .builder()
      .slug(JHLiteModuleSlug.JAVA_BASE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Java", "Add Base classes and Error domain to project")
      .organization(JHipsterModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).build())
      .tags("server")
      .factory(javaBase::build);
  }
}
