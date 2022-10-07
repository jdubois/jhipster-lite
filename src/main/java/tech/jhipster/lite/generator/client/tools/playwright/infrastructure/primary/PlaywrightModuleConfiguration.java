package tech.jhipster.lite.generator.client.tools.playwright.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.tools.playwright.application.PlaywrightApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class PlaywrightModuleConfiguration {

  @Bean
  JHipsterModuleResource playwrightModule(PlaywrightApplicationService playwright) {
    return JHipsterModuleResource
      .builder()
      .slug(PLAYWRIGHT)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addServerPort().build())
      .apiDoc("E2e", "Add Playwright")
      .organization(JHipsterModuleOrganization.builder().feature(FRONT_BROWSER_TEST).addDependency(INIT).build())
      .tags("client", "test", "playwright", "e2e")
      .factory(playwright::buildModule);
  }
}
