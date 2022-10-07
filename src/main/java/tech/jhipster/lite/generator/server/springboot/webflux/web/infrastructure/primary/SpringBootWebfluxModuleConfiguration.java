package tech.jhipster.lite.generator.server.springboot.webflux.web.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.webflux.web.application.SpringBootWebfluxApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class SpringBootWebfluxModuleConfiguration {

  @Bean
  JHipsterModuleResource springBootWebfluxModule(SpringBootWebfluxApplicationService webflux) {
    return JHipsterModuleResource
      .builder()
      .slug(SPRING_BOOT_WEBFLUX_NETTY)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addServerPort().build())
      .apiDoc("Spring Boot - Webflux", "Add Spring Boot Webflux with Netty")
      .organization(JHipsterModuleOrganization.builder().feature(SPRING_SERVER).addDependency(SPRING_BOOT).build())
      .tags("server", "webflux")
      .factory(webflux::buildModule);
  }
}
