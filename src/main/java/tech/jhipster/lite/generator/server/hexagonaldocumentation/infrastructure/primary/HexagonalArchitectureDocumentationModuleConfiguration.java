package tech.jhipster.lite.generator.server.hexagonaldocumentation.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.hexagonaldocumentation.application.HexagonalArchitectureDocumentationApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class HexagonalArchitectureDocumentationModuleConfiguration {

  @Bean
  JHipsterModuleResource hexagonalArchitectureDocumentationModule(
    HexagonalArchitectureDocumentationApplicationService hexagonalArchitectureDocumentations
  ) {
    return JHipsterModuleResource
      .builder()
      .slug("application-service-hexagonal-architecture-documentation")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot", "Add documentation for hexagonal architecture"))
      .standalone()
      .tags("server", "documentation")
      .factory(hexagonalArchitectureDocumentations::buildModule);
  }
}
