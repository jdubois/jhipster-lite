package tech.jhipster.lite.generator.server.springboot.mvc.dummy.liquibase.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.dummy.liquibase.application.DummyLiquibaseApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class DummyLiquibaseModuleConfiguration {

  @Bean
  JHipsterModuleResource dummyLiquibaseModule(DummyLiquibaseApplicationService dummyLiquibase) {
    return JHipsterModuleResource
      .builder()
      .slug(DUMMY_LIQUIBASE_CHANGELOG)
      .withoutProperties()
      .apiDoc("Spring Boot - MVC", "Add liquibase changelog for dummy feature")
      .organization(
        JHipsterModuleOrganization.builder().feature(DUMMY_SCHEMA).addDependency(LIQUIBASE).addDependency(DUMMY_FEATURE).build()
      )
      .tags("server")
      .factory(dummyLiquibase::buildModule);
  }
}
