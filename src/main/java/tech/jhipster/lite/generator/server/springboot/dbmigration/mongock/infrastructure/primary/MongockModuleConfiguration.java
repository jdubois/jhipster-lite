package tech.jhipster.lite.generator.server.springboot.dbmigration.mongock.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.dbmigration.mongock.application.MongockApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class MongockModuleConfiguration {

  @Bean
  JHipsterModuleResource mongockModule(MongockApplicationService mongock) {
    return JHipsterModuleResource
      .builder()
      .slug("mongock")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Database Migration", "Add Mongock"))
      .organization(JHipsterModuleOrganization.builder().addModuleDependency("mongodb").build())
      .tags("server", "spring", "spring-boot", "database", "migration", "mongo-db")
      .factory(mongock::buildModule);
  }
}
