package tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.application.SpringBootActuatorApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class SpringBootActuatorModuleConfiguration {

  @Bean
  JHipsterModuleResource springBootActuatorModule(SpringBootActuatorApplicationService actuatorModule) {
    return JHipsterModuleResource
      .builder()
      .slug(SPRING_BOOT_ACTUATOR)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Spring Boot", "Add Spring Boot Actuator to the project")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_SERVER).build())
      .tags("server", "spring", "spring-boot")
      .factory(actuatorModule::buildSpringBootActuatorModule);
  }
}
