package tech.jhipster.lite.generator.server.sonarqube.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.sonarqube.application.SonarQubeApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class SonarQubeModuleConfiguration {

  @Bean
  JHipsterModuleResource sonarBackendModule(SonarQubeApplicationService sonar) {
    return JHipsterModuleResource
      .builder()
      .slug(SONAR_QUBE_JAVA_BACKEND)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("Sonar", "Add Sonar configuration for Java Backend to inspect code quality")
      .organization(JHipsterModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).build())
      .tags("server", "sonar")
      .factory(sonar::buildBackendModule);
  }

  @Bean
  JHipsterModuleResource sonarBackendFrontendModule(SonarQubeApplicationService sonar) {
    return JHipsterModuleResource
      .builder()
      .slug(SONAR_QUBE_JAVA_BACKEND_AND_FRONTEND)
      .propertiesDefinition(propertiesDefinition())
      .apiDoc("Sonar", "Add Sonar configuration for Java Backend and Frontend to inspect code quality")
      .organization(JHipsterModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).build())
      .tags("server", "sonar")
      .factory(sonar::buildBackendFrontendModule);
  }

  private JHipsterModulePropertiesDefinition propertiesDefinition() {
    return JHipsterModulePropertiesDefinition.builder().addProjectName().addProjectBaseName().addIndentation().build();
  }
}
