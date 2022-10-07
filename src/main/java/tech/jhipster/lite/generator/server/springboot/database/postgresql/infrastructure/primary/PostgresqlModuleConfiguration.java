package tech.jhipster.lite.generator.server.springboot.database.postgresql.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.database.postgresql.application.PostgresqlApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class PostgresqlModuleConfiguration {

  public static final String URL_POSTGRESQL_MODULE = "/api/servers/spring-boot/databases/postgresql";

  @Bean
  JHipsterModuleResource postgresqlModule(PostgresqlApplicationService postgresql) {
    return JHipsterModuleResource
      .builder()
      .slug(POSTGRESQL)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc("Spring Boot - Database", "Add Postgresql to project")
      .organization(JHipsterModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(SPRING_BOOT).build())
      .tags("server", "spring", "spring-boot", "database")
      .factory(postgresql::build);
  }
}
