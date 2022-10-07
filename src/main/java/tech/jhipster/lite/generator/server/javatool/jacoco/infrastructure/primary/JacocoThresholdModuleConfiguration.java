package tech.jhipster.lite.generator.server.javatool.jacoco.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.javatool.jacoco.application.JacocoApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class JacocoThresholdModuleConfiguration {

  @Bean
  JHipsterModuleResource jacocoModuleThreshold(JacocoApplicationService jacoco) {
    return JHipsterModuleResource
      .builder()
      .slug(JACOCO_CHECK_MIN_COVERAGE)
      .withoutProperties()
      .apiDoc("Java", "Add JaCoCo configuration to check minimum coverage")
      .organization(JHipsterModuleOrganization.builder().addDependency(JAVA_BUILD_TOOL).build())
      .tags("server", "tools", "coverage")
      .factory(jacoco::buildModule);
  }
}
