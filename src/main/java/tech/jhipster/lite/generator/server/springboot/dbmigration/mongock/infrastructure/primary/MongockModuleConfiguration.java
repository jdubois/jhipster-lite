package tech.jhipster.lite.generator.server.springboot.dbmigration.mongock.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.dbmigration.mongock.application.MongockApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class MongockModuleConfiguration {

  @Bean
  JHipsterModuleResource mongockModule(MongockApplicationService mongock) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/database-migration-tools/mongock")
      .slug("mongock")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Database Migration", "Add Mongock"))
      .tags("server", "spring", "spring-boot", "database", "migration", "mongo-db")
      .factory(mongock::buildModule);
  }
}
