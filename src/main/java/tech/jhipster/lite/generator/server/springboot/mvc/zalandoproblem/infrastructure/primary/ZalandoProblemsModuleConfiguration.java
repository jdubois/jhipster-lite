package tech.jhipster.lite.generator.server.springboot.mvc.zalandoproblem.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.zalandoproblem.application.ZalandoProblemsApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class ZalandoProblemsModuleConfiguration {

  @Bean
  JHipsterModuleResource zalandoProblemsModule(ZalandoProblemsApplicationService zalandoProblems) {
    return JHipsterModuleResource
      .builder()
      .slug(ZALANDO_PROBLEMS)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc("Spring Boot - MVC", "Zalando problems and error handler")
      .organization(JHipsterModuleOrganization.builder().feature(WEB_ERROR_MANAGEMENT).addDependency(SPRING_SERVER).build())
      .tags("server", "spring", "spring-boot", "mvc", "problem")
      .factory(zalandoProblems::buildModule);
  }
}
