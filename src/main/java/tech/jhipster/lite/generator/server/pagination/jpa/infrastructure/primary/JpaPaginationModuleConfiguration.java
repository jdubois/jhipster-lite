package tech.jhipster.lite.generator.server.pagination.jpa.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.pagination.jpa.application.JpaPaginationModuleApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class JpaPaginationModuleConfiguration {

  @Bean
  JHipsterModuleResource jpaPaginationModule(JpaPaginationModuleApplicationService jpaPagination) {
    return JHipsterModuleResource
      .builder()
      .slug(JPA_PAGINATION)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Pagination", "Add utility class for JPA pagination")
      .organization(JHipsterModuleOrganization.builder().addDependency(PAGINATION_DOMAIN).addDependency(JPA_PERSISTENCE).build())
      .tags("server")
      .factory(jpaPagination::buildModule);
  }
}
