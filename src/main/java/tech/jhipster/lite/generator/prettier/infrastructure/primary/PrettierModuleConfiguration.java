package tech.jhipster.lite.generator.prettier.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.prettier.application.PrettierApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class PrettierModuleConfiguration {

  @Bean
  JHipsterModuleResource prettierModule(PrettierApplicationService prettier) {
    return JHipsterModuleResource
      .builder()
      .slug(PRETTIER)
      .propertiesDefinition(initPropertiesDefinition())
      .apiDoc("Prettier", "Format project with prettier")
      .organization(JHipsterModuleOrganization.builder().addDependency(INIT).build())
      .tags("server", "client", "init")
      .factory(prettier::buildModule);
  }

  private JHipsterModulePropertiesDefinition initPropertiesDefinition() {
    return JHipsterModulePropertiesDefinition.builder().addProjectBaseName().addProjectName().addEndOfLine().addIndentation().build();
  }
}
