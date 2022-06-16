package tech.jhipster.lite.generator.server.springboot.database.postgresql.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.generator.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.generator.module.infrastructure.primary.JHipsterModuleResource;
import tech.jhipster.lite.generator.server.springboot.database.postgresql.application.PostgresqlApplicationService;

@Configuration
class PostgresqlModuleConfiguration {

  public static final String URL_POSTGRESQL_MODULE = "/api/servers/spring-boot/databases/postgresql";

  @Bean
  JHipsterModuleResource postgresqlModule(PostgresqlApplicationService postgresql) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl(URL_POSTGRESQL_MODULE)
      .slug("postgresql")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Database", "Add Postgresql to project"))
      .factory(postgresql::build);
  }
}
