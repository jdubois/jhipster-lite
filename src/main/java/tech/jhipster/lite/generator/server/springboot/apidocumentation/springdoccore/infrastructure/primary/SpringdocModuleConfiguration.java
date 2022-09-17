package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoccore.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoccore.application.SpringdocApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class SpringdocModuleConfiguration {

  private static final String API_GROUP = "Spring Boot - API Documentation";
  private static final String SERVER_TAG = "server";
  private static final String SWAGGER_TAG = "server";
  private static final String SPRING_TAG = "spring";
  private static final String SPRING_BOOT_TAG = "spring-boot";
  private static final String DOCUMENTATION_TAG = "documentation";

  @Bean
  JHipsterModuleResource springdocMvcModule(SpringdocApplicationService springdocApplicationService) {
    return JHipsterModuleResource
      .builder()
      .slug("springdoc-mvc-openapi")
      .propertiesDefinition(buildPropertiesDefinition())
      .apiDoc(API_GROUP, "Add springdoc-openapi for spring MVC")
      .organization(JHipsterModuleOrganization.builder().feature("springdoc").addModuleDependency("spring-boot-tomcat").build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, DOCUMENTATION_TAG, SWAGGER_TAG)
      .factory(springdocApplicationService::buildSpringdocMvcModule);
  }

  @Bean
  JHipsterModuleResource springdocWebfluxModule(SpringdocApplicationService springdocApplicationService) {
    return JHipsterModuleResource
      .builder()
      .slug("springdoc-webflux-openapi")
      .propertiesDefinition(buildPropertiesDefinition())
      .apiDoc(API_GROUP, "Add springdoc-openapi for webflux")
      .organization(JHipsterModuleOrganization.builder().feature("springdoc").addModuleDependency("spring-boot-webflux-netty").build())
      .tags(SERVER_TAG, SPRING_TAG, SPRING_BOOT_TAG, DOCUMENTATION_TAG, SWAGGER_TAG)
      .factory(springdocApplicationService::buildSpringdocWebfluxModule);
  }

  private JHipsterModulePropertiesDefinition buildPropertiesDefinition() {
    return JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build();
  }
}
