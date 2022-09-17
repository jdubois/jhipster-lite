package tech.jhipster.lite.generator.server.springboot.mvc.dummy.flyway.infrastructure.primary;

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
      .slug("dummy-postgresql-flyway-changelog")
      .withoutProperties()
      .apiDoc("Spring Boot - MVC", "Add postgresql flyway changelog for dummy feature")
      .organization(
        JHipsterModuleOrganization
          .builder()
          .feature("dummy-schema")
          .addModuleDependency("flyway")
          .addModuleDependency("dummy-feature")
          .addModuleDependency("postgresql")
          .build()
      )
      .tags("server")
      .factory(dummyFlyway::buildPostgresqlModule);
  }

  @Bean
  JHipsterModuleResource dummyFlywayNotPostgresqlModule(DummyFlywayApplicationService dummyFlyway) {
    return JHipsterModuleResource
      .builder()
      .slug("dummy-not-postgresql-flyway-changelog")
      .withoutProperties()
      .apiDoc("Spring Boot - MVC", "Add not postgresql flyway changelog for dummy feature")
      .organization(
        JHipsterModuleOrganization
          .builder()
          .feature("dummy-schema")
          .addModuleDependency("flyway")
          .addModuleDependency("dummy-feature")
          .build()
      )
      .tags("server")
      .factory(dummyFlyway::buildNotPostgresqlModule);
  }
}
