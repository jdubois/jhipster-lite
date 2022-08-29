package tech.jhipster.lite.generator.client.tools.cypress.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.tools.cypress.application.CypressApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class CypressModuleConfiguration {

  @Bean
  JHipsterModuleResource cypresModule(CypressApplicationService cypress) {
    return JHipsterModuleResource
      .builder()
      .slug("cypress")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addServerPort().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("E2e", "Add Cypress"))
      .organization(JHipsterModuleOrganization.builder().feature("front-browser-test").addModuleDependency("init").build())
      .tags("client", "test", "cypress", "e2e")
      .factory(cypress::buildModule);
  }
}
