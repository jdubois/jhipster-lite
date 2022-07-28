package tech.jhipster.lite.generator.server.springboot.cache.simple.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.cache.simple.application.SpringBootCacheSimpleApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class CacheModuleConfiguration {

  @Bean
  JHipsterModuleResource simpleCacheModule(SpringBootCacheSimpleApplicationService caches) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/caches/simple")
      .slug("springboot-cache")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Cache", "Add simple cache"))
      .tags("server", "spring", "spring-boot", "cache")
      .factory(caches::buildModule);
  }
}
