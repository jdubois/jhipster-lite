package tech.jhipster.lite.generator.client.react.core.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.react.core.application.ReactCoreApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class ReactCoreModulesConfiguration {

  public static final String REACT = "react";

  @Bean
  JHipsterModuleResource reactCoreModule(ReactCoreApplicationService react) {
    return JHipsterModuleResource
      .builder()
      .slug("react-core")
      .propertiesDefinition(properties())
      .apiDoc(new JHipsterModuleApiDoc("React", "Add React+Vite with minimal CSS"))
      .organization(JHipsterModuleOrganization.builder().feature("client-core").addModuleDependency("init").build())
      .tags("client", REACT)
      .factory(react::buildModule);
  }

  private JHipsterModulePropertiesDefinition properties() {
    return JHipsterModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build();
  }
}
