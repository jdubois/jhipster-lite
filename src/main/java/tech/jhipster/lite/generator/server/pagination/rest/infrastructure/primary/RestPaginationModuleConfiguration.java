package tech.jhipster.lite.generator.server.pagination.rest.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

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
      .slug(REST_PAGINATION)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Pagination", "Add rest models for pagination handling")
      .organization(JHipsterModuleOrganization.builder().addDependency(PAGINATION_DOMAIN).addDependency(SPRINGDOC).build())
      .tags("server")
      .factory(restPagination::buildModule);
  }
}
