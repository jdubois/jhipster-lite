package tech.jhipster.lite.generator.server.springboot.springcloud.eureka.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.springcloud.eureka.application.EurekaApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class EurekaModuleConfiguration {

  @Bean
  JHipsterModuleResource eurekaModule(EurekaApplicationService eureka) {
    return JHipsterModuleResource
      .builder()
      .slug("eureka-client")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Eureka Client"))
      .organization(JHipsterModuleOrganization.builder().feature("service-discovery").addModuleDependency("spring-cloud").build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(eureka::buildModule);
  }
}
