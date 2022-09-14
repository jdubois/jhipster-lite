package tech.jhipster.lite.generator.server.pagination.jpa.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.pagination.jpa.application.JpaPaginationModuleApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class JpaPaginationModuleConfiguration {

  @Bean
  JHipsterModuleResource jpaPaginationModule(JpaPaginationModuleApplicationService jpaPagination) {
    return JHipsterModuleResource
      .builder()
      .slug("jpa-pagination")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc(new JHipsterModuleApiDoc("Pagination", "Add utility class for JPA pagination"))
      .organization(
        JHipsterModuleOrganization.builder().addModuleDependency("pagination-domain").addFeatureDependency("jpa-persistence").build()
      )
      .tags("server")
      .factory(jpaPagination::buildModule);
  }
}
