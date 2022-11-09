package tech.jhipster.lite.generator.server.springboot.database.postgresqldialect.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.database.postgresqldialect.application.PostgresqlDialectApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class PostgresqlDialectModuleConfiguration {

  @Bean
  JHipsterModuleResource postgresqlDialectModule(PostgresqlDialectApplicationService postgresqldialect) {
    return JHipsterModuleResource
      .builder()
      .slug(POSTGRESQL_DIALECT)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc("Spring Boot - Database", "Add PostgreSQL custom Dialect for Blob to project")
      .organization(JHipsterModuleOrganization.builder().addDependency(POSTGRESQL).build())
      .tags("server", "spring", "spring-boot", "database")
      .factory(postgresqldialect::build);
  }
}
