package tech.jhipster.lite.generator.server.springboot.database.redis.infrastructure.primary;

import static tech.jhipster.lite.generator.slug.domain.JHLiteModuleSlug.REDIS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.database.redis.application.RedisApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class RedisModuleConfiguration {

  @Bean
  JHipsterModuleResource redisModule(RedisApplicationService redis) {
    return JHipsterModuleResource
      .builder()
      .slug(REDIS)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addConfigurationFormat().build())
      .apiDoc("Spring Boot - Database", "Add Redis drivers and dependencies, with testcontainers")
      .organization(JHipsterModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "database")
      .factory(redis::buildModule);
  }
}
