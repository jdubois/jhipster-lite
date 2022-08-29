package tech.jhipster.lite.generator.server.springboot.cache.ehcache.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.cache.ehcache.application.EhcacheApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class EHCacheModulesConfiguration {

  private static final String SPRING_BOOT = "spring-boot";

  @Bean
  JHipsterModuleResource javaEHCacheModule(EhcacheApplicationService ehCaches) {
    return JHipsterModuleResource
      .builder()
      .slug("ehcache-with-java-config")
      .propertiesDefinition(properties())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Cache", "Add Ehcache with Java configuration"))
      .organization(organization())
      .tags("server", "spring", SPRING_BOOT, "cache")
      .factory(ehCaches::buildJavaConfigurationModule);
  }

  @Bean
  JHipsterModuleResource xmlEHCacheModule(EhcacheApplicationService ehCaches) {
    return JHipsterModuleResource
      .builder()
      .slug("ehcache-with-xml-config")
      .propertiesDefinition(properties())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Cache", "Add Ehcache with XML configuration"))
      .organization(organization())
      .tags("server", "spring", SPRING_BOOT, "cache")
      .factory(ehCaches::buildXmlConfigurationModule);
  }

  private JHipsterModulePropertiesDefinition properties() {
    return JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build();
  }

  private JHipsterModuleOrganization organization() {
    return JHipsterModuleOrganization.builder().feature("ehcache").addModuleDependency(SPRING_BOOT).build();
  }
}
