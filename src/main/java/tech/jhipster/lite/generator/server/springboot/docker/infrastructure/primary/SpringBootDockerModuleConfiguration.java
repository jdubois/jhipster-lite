package tech.jhipster.lite.generator.server.springboot.docker.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.docker.application.SpringBootDockerApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class SpringBootDockerModuleConfiguration {

  @Bean
  JHipsterModuleResource jibModule(SpringBootDockerApplicationService springBootDocker) {
    return JHipsterModuleResource
      .builder()
      .slug("jib")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addServerPort().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Tools", "Add Docker image building with Jib"))
      .organization(organization())
      .tags("server", "spring", "spring-boot", "jib")
      .factory(springBootDocker::buildJibModule);
  }

  @Bean
  JHipsterModuleResource dockerFileModule(SpringBootDockerApplicationService springBootDocker) {
    return JHipsterModuleResource
      .builder()
      .slug("dockerfile")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addServerPort().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Tools", "Add Dockerfile"))
      .organization(organization())
      .tags("server", "spring", "spring-boot", "docker")
      .factory(springBootDocker::buildDockerFileModule);
  }

  private JHipsterModuleOrganization organization() {
    return JHipsterModuleOrganization.builder().addFeatureDependency("java-build-tool").build();
  }
}
