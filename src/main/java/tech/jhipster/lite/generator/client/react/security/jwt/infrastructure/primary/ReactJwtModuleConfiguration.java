package tech.jhipster.lite.generator.client.react.security.jwt.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.react.security.jwt.application.ReactJwtApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class ReactJwtModuleConfiguration {

  @Bean
  JHipsterModuleResource reactJwtModule(ReactJwtApplicationService reactJwt) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/clients/react/jwt")
      .slug("react-jwt")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("React", "Add JWT Login React"))
      .tags("client", "react", "jwt")
      .factory(reactJwt::buildModule);
  }
}
