package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application.LiquibaseApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class LiquibaseModuleConfiguration {

  @Bean
  JHipsterModuleResource liquibaseModule(LiquibaseApplicationService liquibase) {
    return JHipsterModuleResource
      .builder()
      .slug("liquibase")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addIndentation().addBasePackage().build())
      .apiDoc("Spring Boot - Database Migration", "Add Liquibase")
      .organization(JHipsterModuleOrganization.builder().feature("database-migration").addFeatureDependency("jpa-persistence").build())
      .tags("server", "spring", "spring-boot", "database", "migration", "liquibase")
      .factory(liquibase::buildModule);
  }
}
