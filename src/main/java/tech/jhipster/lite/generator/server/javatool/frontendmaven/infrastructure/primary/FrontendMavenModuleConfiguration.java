package tech.jhipster.lite.generator.server.javatool.frontendmaven.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.javatool.frontendmaven.application.FrontendMavenApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class FrontendMavenModuleConfiguration {

  @Bean
  JHipsterModuleResource frontendMavenModule(FrontendMavenApplicationService frontendMaven) {
    return JHipsterModuleResource
      .builder()
      .slug("frontend-maven-plugin")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build())
      .apiDoc("Frontend Maven Plugin", "Add Frontend Maven Plugin")
      .organization(JHipsterModuleOrganization.builder().addFeatureDependency("java-build-tool").build())
      .tags("server", "tools")
      .factory(frontendMaven::buildModule);
  }
}
