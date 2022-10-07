package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.application.FlywayApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class FlywayModuleConfiguration {

  @Bean
  JHipsterModuleResource flywayInitializationModule(FlywayApplicationService flyway) {
    return JHipsterModuleResource
      .builder()
      .slug(FLYWAY)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - Database Migration", "Add Flyway")
      .organization(JHipsterModuleOrganization.builder().feature(DATABASE_MIGRATION).addDependency(JPA_PERSISTENCE).build())
      .tags("server", "spring", "spring-boot", "database", "migration")
      .factory(flyway::buildInitializationModule);
  }

  @Bean
  JHipsterModuleResource flywayMysqlModule(FlywayApplicationService flyway) {
    return JHipsterModuleResource
      .builder()
      .slug(FLYWAY_MYSQL)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - Database Migration", "Add Flyway mysql")
      .organization(JHipsterModuleOrganization.builder().addDependency(FLYWAY).addDependency(MYSQL).build())
      .tags("server", "spring", "spring-boot", "database", "migration")
      .factory(flyway::buildMysqlDependencyModule);
  }
}
