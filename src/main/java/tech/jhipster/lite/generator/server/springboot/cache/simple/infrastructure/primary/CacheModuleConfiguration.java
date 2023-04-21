package tech.jhipster.lite.generator.server.springboot.cache.simple.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteModuleSlug.SPRING_BOOT;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.SPRING_BOOT_CACHE;

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
      .organization(organization())
      .tags("server", "spring", "spring-boot", "cache")
      .factory(caches::buildModule);
  }

  private JHipsterModuleOrganization organization() {
    return JHipsterModuleOrganization.builder().addDependency(SPRING_BOOT).build();
  }
}
