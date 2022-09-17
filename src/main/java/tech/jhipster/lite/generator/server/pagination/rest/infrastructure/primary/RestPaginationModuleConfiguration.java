package tech.jhipster.lite.generator.server.pagination.rest.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.pagination.rest.application.RestPaginationModuleApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class RestPaginationModuleConfiguration {

  @Bean
  JHipsterModuleResource restPaginationModule(RestPaginationModuleApplicationService restPagination) {
    return JHipsterModuleResource
      .builder()
      .slug("rest-pagination")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Pagination", "Add rest models for pagination handling")
      .organization(JHipsterModuleOrganization.builder().addModuleDependency("pagination-domain").addFeatureDependency("springdoc").build())
      .tags("server")
      .factory(restPagination::buildModule);
  }
}
