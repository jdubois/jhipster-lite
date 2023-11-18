package tech.jhipster.lite.generator.server.springboot.database.hibernate2ndlevelcache.infrastructure.primary;

import static tech.jhipster.lite.generator.slug.domain.JHLiteFeatureSlug.JCACHE;
import static tech.jhipster.lite.generator.slug.domain.JHLiteFeatureSlug.JPA_PERSISTENCE;
import static tech.jhipster.lite.generator.slug.domain.JHLiteModuleSlug.HIBERNATE_2ND_LEVEL_CACHE;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.database.hibernate2ndlevelcache.application.Hibernate2ndLevelCacheApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class Hibernate2ndLevelCacheModuleConfiguration {

  @Bean
  JHipsterModuleResource hibernate2ndLevelCacheModule(Hibernate2ndLevelCacheApplicationService hibernate2ndLevelCacheApplicationService) {
    return JHipsterModuleResource
      .builder()
      .slug(HIBERNATE_2ND_LEVEL_CACHE)
      .propertiesDefinition(
        JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().addConfigurationFormat().build()
      )
      .apiDoc("Spring Boot - Database", "Add Hibernate second level cache configuration to project")
      .organization(JHipsterModuleOrganization.builder().addDependency(JCACHE).addDependency(JPA_PERSISTENCE).build())
      .tags("server", "spring", "spring-boot", "database", "hibernate", "cache")
      .factory(hibernate2ndLevelCacheApplicationService::build);
  }
}
