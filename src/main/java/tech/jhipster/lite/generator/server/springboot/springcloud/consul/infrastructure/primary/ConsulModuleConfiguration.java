package tech.jhipster.lite.generator.server.springboot.springcloud.consul.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.springcloud.consul.application.ConsulApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class ConsulModuleConfiguration {

  @Bean
  JHipsterModuleResource consulModule(ConsulApplicationService consulApplicationService) {
    return JHipsterModuleResource
      .builder()
      .slug("consul")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addProjectBaseName().build())
      .apiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Consul config and discovery")
      .organization(JHipsterModuleOrganization.builder().feature("service-discovery").addModuleDependency("spring-boot-actuator").build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(consulApplicationService::buildModule);
  }
}
