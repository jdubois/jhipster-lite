package tech.jhipster.lite.generator.client.vue.core.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.vue.core.application.VueApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
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
      .apiDoc(new JHipsterModuleApiDoc("Vue", "Add Vue+Vite"))
      .organization(JHipsterModuleOrganization.builder().feature("client-core").addModuleDependency("init").build())
      .tags("client", "init", "vue")
      .factory(vue::buildVueModule);
  }

  @Bean
  JHipsterModuleResource vuePiniaModule(VueApplicationService vue) {
    return JHipsterModuleResource
      .builder()
      .slug("vue-pinia")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc("Vue", "Add pinia for state management"))
      .organization(JHipsterModuleOrganization.builder().addModuleDependency("vue-core").build())
      .tags("client", "vue", "store")
      .factory(vue::buildPiniaModule);
  }
}
