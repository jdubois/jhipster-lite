package tech.jhipster.lite.generator.server.sonar.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.sonar.application.SonarApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class SonarModuleConfiguration {

  @Bean
  JHipsterModuleResource sonarBackendModule(SonarApplicationService sonar) {
    return JHipsterModuleResource
      .builder()
      .slug("sonar-java-backend")
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("Sonar", "Add Sonar configuration for Java Backend to inspect code quality")
      .organization(JHipsterModuleOrganization.builder().addFeatureDependency("java-build-tool").build())
      .tags("server", "sonar")
      .factory(sonar::buildBackendModule);
  }

  @Bean
  JHipsterModuleResource sonarBackendFrontendModule(SonarApplicationService sonar) {
    return JHipsterModuleResource
      .builder()
      .slug("sonar-java-backend-and-frontend")
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("Sonar", "Add Sonar configuration for Java Backend and Frontend to inspect code quality")
      .organization(JHipsterModuleOrganization.builder().addFeatureDependency("java-build-tool").build())
      .tags("server", "sonar")
      .factory(sonar::buildBackendFrontendModule);
  }

  private JHipsterModulePropertiesDefinition propertiesDefinition() {
    return JHipsterModulePropertiesDefinition.builder().addProjectName().addProjectBaseName().addIndentation().build();
  }
}
