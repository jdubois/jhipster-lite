package tech.jhipster.lite.generator.server.springboot.dbmigration.mongock.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.dbmigration.mongock.application.MongockApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class MongockModuleConfiguration {

  @Bean
  JHipsterModuleResource mongockModule(MongockApplicationService mongock) {
    return JHipsterModuleResource
      .builder()
      .slug(MONGOCK)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - Database Migration", "Add Mongock")
      .organization(JHipsterModuleOrganization.builder().addDependency(MONGODB).build())
      .tags("server", "spring", "spring-boot", "database", "migration", "mongo-db")
      .factory(mongock::buildModule);
  }
}
