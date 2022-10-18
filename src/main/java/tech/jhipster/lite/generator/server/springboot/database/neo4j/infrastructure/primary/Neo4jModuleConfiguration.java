package tech.jhipster.lite.generator.server.springboot.database.neo4j.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.*;
import tech.jhipster.lite.generator.server.springboot.database.neo4j.application.*;
import tech.jhipster.lite.module.domain.resource.*;

@Configuration
class Neo4jModuleConfiguration {

  @Bean
  JHipsterModuleResource neo4jModule(Neo4jApplicationService applicationService) {
    return JHipsterModuleResource
      .builder()
      .slug(NEO4J)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc("Spring Boot - Database", "Add Neo4j drivers and dependencies, with testcontainers")
      .organization(JHipsterModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "database")
      .factory(applicationService::build);
  }
}
