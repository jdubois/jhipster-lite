package tech.jhipster.lite.generator.client.react.core.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.react.core.application.ReactCoreApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class ReactCodeModulesConfiguration {

  @Bean
  JHipsterModuleResource notStyledReactCoreModule(ReactCoreApplicationService react) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/clients/react")
      .slug("react")
      .propertiesDefinition(properties())
      .apiDoc(new JHipsterModuleApiDoc("React", "Init React+Vite"))
      .factory(react::buildModuleWithoutStyle);
  }

  @Bean
  JHipsterModuleResource styledReactCoreModule(ReactCoreApplicationService react) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/clients/react/styles")
      .slug("react-styled")
      .propertiesDefinition(properties())
      .apiDoc(new JHipsterModuleApiDoc("React", "Add React+Vite with minimal CSS"))
      .factory(react::buildModuleWithStyle);
  }

  private JHipsterModulePropertiesDefinition properties() {
    return JHipsterModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build();
  }
}
