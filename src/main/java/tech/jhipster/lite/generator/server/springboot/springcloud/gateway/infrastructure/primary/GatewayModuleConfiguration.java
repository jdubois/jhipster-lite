package tech.jhipster.lite.generator.server.springboot.springcloud.gateway.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.springcloud.gateway.application.GatewayApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class GatewayModuleConfiguration {

  @Bean
  JHipsterModuleResource gatewayModule(GatewayApplicationService gateway) {
    return JHipsterModuleResource
      .builder()
      .slug(GATEWAY)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Spring Boot - Spring Cloud", "Add Spring Cloud Gateway")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_CLOUD).build())
      .tags("server", "spring", "spring-boot", "cloud")
      .factory(gateway::buildModule);
  }
}
