package tech.jhipster.lite.generator.client.vue.core.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.vue.core.application.VueApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class VueModuleConfiguration {

  @Bean
  JHipsterModuleResource vueCoreModule(VueApplicationService vue) {
    return JHipsterModuleResource
      .builder()
      .slug(VUE_CORE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc("Vue", "Add Vue+Vite")
      .organization(JHipsterModuleOrganization.builder().feature(CLIENT_CORE).addDependency(INIT).addDependency(PRETTIER).build())
      .tags("client", "init", "vue")
      .factory(vue::buildVueModule);
  }

  @Bean
  JHipsterModuleResource vuePiniaModule(VueApplicationService vue) {
    return JHipsterModuleResource
      .builder()
      .slug(VUE_PINIA)
      .withoutProperties()
      .apiDoc("Vue", "Add pinia for state management")
      .organization(JHipsterModuleOrganization.builder().addDependency(VUE_CORE).build())
      .tags("client", "vue", "store")
      .factory(vue::buildPiniaModule);
  }
}
