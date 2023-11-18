package tech.jhipster.lite.generator.server.springboot.database.neo4j.infrastructure.primary;

import static tech.jhipster.lite.generator.slug.domain.JHLiteModuleSlug.NEO4J;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.database.neo4j.application.Neo4jApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class Neo4jModuleConfiguration {

  @Bean
  JHipsterModuleResource neo4jModule(Neo4jApplicationService applicationService) {
    return JHipsterModuleResource
      .builder()
      .slug(NEO4J)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().addConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database", "Add Neo4j drivers and dependencies, with testcontainers")
      .organization(JHipsterModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "database")
      .factory(applicationService::build);
  }
}
