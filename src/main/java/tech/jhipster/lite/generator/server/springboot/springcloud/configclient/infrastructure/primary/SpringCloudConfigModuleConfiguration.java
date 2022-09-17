package tech.jhipster.lite.generator.server.springboot.springcloud.configclient.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.springcloud.configclient.application.SpringCloudConfigClientApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class SpringCloudConfigModuleConfiguration {

  @Bean
  JHipsterModuleResource springCloudConfigModule(SpringCloudConfigClientApplicationService cloudConfigs) {
    return JHipsterModuleResource
      .builder()
      .slug("spring-cloud")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addProjectBaseName().build())
      .apiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Config Client")
      .organization(JHipsterModuleOrganization.builder().feature("service-discovery").addModuleDependency("spring-boot-actuator").build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(cloudConfigs::buildModule);
  }
}
