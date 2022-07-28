package tech.jhipster.lite.generator.client.tools.cypress.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.tools.cypress.application.CypressApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class CypressModuleConfiguration {

  @Bean
  JHipsterModuleResource cypresModule(CypressApplicationService cypress) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/clients/cypress")
      .slug("cypress")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addServerPort().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("E2e", "Add Cypress"))
      .tags("client", "cypress", "e2e")
      .factory(cypress::buildModule);
  }
}
