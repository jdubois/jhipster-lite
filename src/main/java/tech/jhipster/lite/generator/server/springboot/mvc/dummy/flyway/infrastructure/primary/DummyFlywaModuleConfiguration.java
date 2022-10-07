package tech.jhipster.lite.generator.server.springboot.mvc.dummy.flyway.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.dummy.flyway.application.DummyFlywayApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class DummyFlywaModuleConfiguration {

  @Bean
  JHipsterModuleResource dummyFlywayPostgresqlModule(DummyFlywayApplicationService dummyFlyway) {
    return JHipsterModuleResource
      .builder()
      .slug(DUMMY_POSTGRESQL_FLYWAY_CHANGELOG)
      .withoutProperties()
      .apiDoc("Spring Boot - MVC", "Add postgresql flyway changelog for dummy feature")
      .organization(
        JHipsterModuleOrganization
          .builder()
          .feature(DUMMY_SCHEMA)
          .addDependency(FLYWAY)
          .addDependency(DUMMY_FEATURE)
          .addDependency(POSTGRESQL)
          .build()
      )
      .tags("server")
      .factory(dummyFlyway::buildPostgresqlModule);
  }

  @Bean
  JHipsterModuleResource dummyFlywayNotPostgresqlModule(DummyFlywayApplicationService dummyFlyway) {
    return JHipsterModuleResource
      .builder()
      .slug(DUMMY_NOT_POSTGRESQL_FLYWAY_CHANGELOG)
      .withoutProperties()
      .apiDoc("Spring Boot - MVC", "Add not postgresql flyway changelog for dummy feature")
      .organization(JHipsterModuleOrganization.builder().feature(DUMMY_SCHEMA).addDependency(FLYWAY).addDependency(DUMMY_FEATURE).build())
      .tags("server")
      .factory(dummyFlyway::buildNotPostgresqlModule);
  }
}
