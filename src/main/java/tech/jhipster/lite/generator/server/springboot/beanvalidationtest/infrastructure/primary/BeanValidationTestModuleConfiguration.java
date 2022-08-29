package tech.jhipster.lite.generator.server.springboot.beanvalidationtest.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.beanvalidationtest.application.BeanValidationTestApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class BeanValidationTestModuleConfiguration {

  @Bean
  JHipsterModuleResource beanValidationtTestModule(BeanValidationTestApplicationService beanValidationTest) {
    return JHipsterModuleResource
      .builder()
      .slug("bean-validation-test")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - MVC", "Add test tools for bean validation"))
      .organization(JHipsterModuleOrganization.SPRINGBOOT_DEPENDENCY)
      .tags("server", "spring", "spring-boot", "validation")
      .factory(beanValidationTest::buildModule);
  }
}
