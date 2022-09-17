package tech.jhipster.lite.generator.setup.codespaces.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.setup.codespaces.application.CodespacesApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertyDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class CodespaceModuleConfiguration {

  @Bean
  JHipsterModuleResource codespaceModule(CodespacesApplicationService codespaces) {
    return JHipsterModuleResource
      .builder()
      .slug("github-codespaces")
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("Codespaces", "Init GitHub Codespaces configuration files")
      .standalone()
      .tags("setup")
      .factory(codespaces::buildModule);
  }

  private JHipsterModulePropertiesDefinition propertiesDefinition() {
    return JHipsterModulePropertiesDefinition.builder().add(serverPort()).build();
  }

  private JHipsterModulePropertyDefinition serverPort() {
    return JHipsterModulePropertyDefinition
      .optionalIntegerProperty("serverPort")
      .description("Application server port")
      .example("8080")
      .order(200)
      .build();
  }
}
