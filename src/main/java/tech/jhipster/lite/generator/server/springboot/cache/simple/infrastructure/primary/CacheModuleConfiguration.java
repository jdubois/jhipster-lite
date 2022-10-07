package tech.jhipster.lite.generator.server.springboot.cache.simple.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.cache.simple.application.SpringBootCacheSimpleApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class CacheModuleConfiguration {

  @Bean
  JHipsterModuleResource simpleCacheModule(SpringBootCacheSimpleApplicationService caches) {
    return JHipsterModuleResource
      .builder()
      .slug(SPRING_BOOT_CACHE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - Cache", "Add simple cache")
      .organization(JHipsterModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "cache")
      .factory(caches::buildModule);
  }
}
