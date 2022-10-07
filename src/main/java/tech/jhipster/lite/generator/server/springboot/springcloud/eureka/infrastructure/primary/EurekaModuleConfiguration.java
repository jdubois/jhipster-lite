package tech.jhipster.lite.generator.server.springboot.springcloud.eureka.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.springcloud.eureka.application.EurekaApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class EurekaModuleConfiguration {

  @Bean
  JHipsterModuleResource eurekaModule(EurekaApplicationService eureka) {
    return JHipsterModuleResource
      .builder()
      .slug(EUREKA_CLIENT)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addProjectBaseName().addIndentation().build())
      .apiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Eureka Client")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_CLOUD).build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(eureka::buildModule);
  }
}
