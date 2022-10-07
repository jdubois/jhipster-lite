package tech.jhipster.lite.generator.server.javatool.arch.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.javatool.arch.application.JavaArchUnitApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class ArchUnitModuleConfiguration {

  @Bean
  JHipsterModuleResource archUnitModule(JavaArchUnitApplicationService archUnit) {
    return JHipsterModuleResource
      .builder()
      .slug(JAVA_ARCHUNIT)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Java", "Add Hexagonal Arch Unit Tests to project")
      .organization(JHipsterModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).addDependency(JAVA_BASE).build())
      .tags("server", "test")
      .factory(archUnit::buildModule);
  }
}
