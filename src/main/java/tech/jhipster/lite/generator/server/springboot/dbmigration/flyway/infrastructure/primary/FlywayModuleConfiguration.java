package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.application.FlywayApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class FlywayModuleConfiguration {

  @Bean
  JHipsterModuleResource flywayInitializationModule(FlywayApplicationService flyway) {
    return JHipsterModuleResource
      .builder()
      .slug("flyway")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Database Migration", "Add Flyway"))
      .organization(JHipsterModuleOrganization.builder().feature("database-migration").addFeatureDependency("jpa-persistence").build())
      .tags("server", "spring", "spring-boot", "database", "migration")
      .factory(flyway::buildInitializationModule);
  }

  @Bean
  JHipsterModuleResource flywayMysqlModule(FlywayApplicationService flyway) {
    return JHipsterModuleResource
      .builder()
      .slug("flyway-mysql")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Database Migration", "Add Flyway mysql"))
      .organization(JHipsterModuleOrganization.builder().addModuleDependency("flyway").addModuleDependency("mysql").build())
      .tags("server", "spring", "spring-boot", "database", "migration")
      .factory(flyway::buildMysqlDependencyModule);
  }
}
