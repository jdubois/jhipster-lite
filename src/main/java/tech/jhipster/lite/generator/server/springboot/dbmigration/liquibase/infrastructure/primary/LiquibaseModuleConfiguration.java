package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application.LiquibaseApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class LiquibaseModuleConfiguration {

  @Bean
  JHipsterModuleResource liquibaseModule(LiquibaseApplicationService liquibase) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/database-migration-tools/liquibase")
      .slug("liquibase")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addIndentation().addBasePackage().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Database Migration", "Add Liquibase"))
      .tags("server", "database", "miigration", "liquibase")
      .factory(liquibase::buildModule);
  }
}
