package tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.application.SpringBootActuatorApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class SpringBootActuatorModuleConfiguration {

  @Bean
  JHipsterModuleResource springBootActuatorModule(SpringBootActuatorApplicationService actuatorModule) {
    return JHipsterModuleResource
      .builder()
      .slug("spring-boot-actuator")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot", "Add Spring Boot Actuator to the project"))
      .organization(JHipsterModuleOrganization.builder().addFeatureDependency("spring-server").build())
      .tags("server", "spring", "spring-boot")
      .factory(actuatorModule::buildSpringBootActuatorModule);
  }
}
