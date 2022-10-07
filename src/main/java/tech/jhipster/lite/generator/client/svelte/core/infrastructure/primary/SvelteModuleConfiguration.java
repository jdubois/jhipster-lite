package tech.jhipster.lite.generator.client.svelte.core.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.svelte.core.application.SvelteApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
public class SvelteModuleConfiguration {

  @Bean
  JHipsterModuleResource svelteModule(SvelteApplicationService svelteApplicationService) {
    return JHipsterModuleResource
      .builder()
      .slug(SVELTE)
      .withoutProperties()
      .apiDoc("Svelte", "Add Svelte")
      .organization(JHipsterModuleOrganization.builder().feature(CLIENT_CORE).addDependency(INIT).addDependency(PRETTIER).build())
      .tags("client", "svelte")
      .factory(svelteApplicationService::buildModule);
  }
}
