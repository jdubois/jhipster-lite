package tech.jhipster.lite.generator.server.springboot.customjhlite.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.customjhlite.application.CustomJHLiteApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class CustomJHLiteModuleConfiguration {

  @Bean
  JHipsterModuleResource customJHLiteModule(CustomJHLiteApplicationService customJHLite) {
    return JHipsterModuleResource
      .builder()
      .slug(CUSTOM_JHLITE)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("JHLite", "Create a custom JHLite instance to build custom modules")
      .organization(JHipsterModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server")
      .factory(customJHLite::buildModule);
  }

  private JHipsterModulePropertiesDefinition propertiesDefinition() {
    return JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().addServerPort().build();
  }
}
