package tech.jhipster.lite.generator.client.vue.core.infrastructure.primary;

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
      .slug("vue-core")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc("Vue", "Add Vue+Vite")
      .organization(
        JHipsterModuleOrganization.builder().feature("client-core").addModuleDependency("init").addModuleDependency("prettier").build()
      )
      .tags("client", "init", "vue")
      .factory(vue::buildVueModule);
  }

  @Bean
  JHipsterModuleResource vuePiniaModule(VueApplicationService vue) {
    return JHipsterModuleResource
      .builder()
      .slug("vue-pinia")
      .withoutProperties()
      .apiDoc("Vue", "Add pinia for state management")
      .organization(JHipsterModuleOrganization.builder().addModuleDependency("vue-core").build())
      .tags("client", "vue", "store")
      .factory(vue::buildPiniaModule);
  }
}
