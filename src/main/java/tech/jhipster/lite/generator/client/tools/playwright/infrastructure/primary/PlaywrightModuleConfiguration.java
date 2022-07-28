package tech.jhipster.lite.generator.client.tools.playwright.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.tools.playwright.application.PlaywrightApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class PlaywrightModuleConfiguration {

  @Bean
  JHipsterModuleResource playwrightModule(PlaywrightApplicationService playwright) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/clients/playwright")
      .slug("client-common-playwright")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addServerPort().build())
      .apiDoc(new JHipsterModuleApiDoc("E2e", "/api/clients/playwright"))
      .tags("client", "test", "playwright")
      .factory(playwright::buildModule);
  }
}
