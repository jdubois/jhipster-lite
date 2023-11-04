package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.DATABASE_MIGRATION;
import static tech.jhipster.lite.generator.JHLiteFeatureSlug.JPA_PERSISTENCE;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.LIQUIBASE;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.LOGS_SPY;

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
      .slug(LIQUIBASE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addIndentation().addBasePackage().addConfigurationFormat().build())
      .apiDoc("Spring Boot - Database Migration", "Add Liquibase")
      .organization(
        JHipsterModuleOrganization.builder().feature(DATABASE_MIGRATION).addDependency(JPA_PERSISTENCE).addDependency(LOGS_SPY).build()
      )
      .tags("server", "spring", "spring-boot", "database", "migration", "liquibase")
      .factory(liquibase::buildModule);
  }
}
