package tech.jhipster.lite.generator.typescript.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.typescript.application.TypescriptApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class TypescriptModuleConfiguration {

  @Bean
  JHipsterModuleResource typescriptModule(TypescriptApplicationService typescriptApplicationService) {
    return JHipsterModuleResource
      .builder()
      .slug("typescript")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc("Typescript", "Init Typescript project"))
      .organization(JHipsterModuleOrganization.builder().addModuleDependency("init").build())
      .tags("typescript")
      .factory(typescriptApplicationService::buildModule);
  }
}
