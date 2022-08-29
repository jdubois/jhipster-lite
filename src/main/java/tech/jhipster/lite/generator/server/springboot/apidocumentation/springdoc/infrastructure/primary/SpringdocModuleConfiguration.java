package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.application.SpringdocApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization.JHipsterModuleOrganizationBuilder;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class SpringdocModuleConfiguration {

  private static final String TAG = "Spring Boot - API Documentation";
  private static final String TAG_SERVER = "server";
  private static final String TAG_SPRING = "spring";
  private static final String TAG_SPRING_BOOT = "spring-boot";
  private static final String TAG_DOCUMENTATION = "documentation";

  @Bean
  JHipsterModuleResource springdocMvcModule(SpringdocApplicationService springdocApplicationService) {
    return JHipsterModuleResource
      .builder()
      .slug("springdoc-mvc-openapi")
      .propertiesDefinition(buildPropertiesDefinition())
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add springdoc-openapi for spring MVC"))
      .organization(springServerDependencyOrganization().build())
      .tags(TAG_SERVER, TAG_SPRING, TAG_SPRING_BOOT, TAG_DOCUMENTATION)
      .factory(springdocApplicationService::buildSpringdocMvcModule);
  }

  @Bean
  JHipsterModuleResource springdocWebfluxModule(SpringdocApplicationService springdocApplicationService) {
    return JHipsterModuleResource
      .builder()
      .slug("springdoc-webflux-openapi")
      .propertiesDefinition(buildPropertiesDefinition())
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add springdoc-openapi for webflux"))
      .organization(webfluxDependencyOrganization().build())
      .tags(TAG_SERVER, TAG_SPRING, TAG_SPRING_BOOT, TAG_DOCUMENTATION)
      .factory(springdocApplicationService::buildSpringdocWebfluxModule);
  }

  @Bean
  JHipsterModuleResource springdocMvcWithSecurityJwtModule(SpringdocApplicationService springdocApplicationService) {
    return JHipsterModuleResource
      .builder()
      .slug("springdoc-mvc-openapi-with-security-jwt")
      .propertiesDefinition(buildPropertiesDefinition())
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add springdoc-openapi with Security JWT for srping MVC"))
      .organization(springServerDependencyOrganization().addModuleDependency("spring-boot-jwt").build())
      .tags(TAG_SERVER, TAG_SPRING, TAG_SPRING_BOOT, TAG_DOCUMENTATION)
      .factory(springdocApplicationService::buildSpringdocMvcModuleWithSecurityJWT);
  }

  @Bean
  JHipsterModuleResource springdocWebfluxWithSecurityJwtModule(SpringdocApplicationService springdocApplicationService) {
    return JHipsterModuleResource
      .builder()
      .slug("springdoc-webflux-openapi-with-security-jwt")
      .propertiesDefinition(buildPropertiesDefinition())
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add springdoc-openapi with Security JWT for webflux"))
      .organization(webfluxDependencyOrganization().addModuleDependency("spring-boot-jwt").build())
      .tags(TAG_SERVER, TAG_SPRING, TAG_SPRING_BOOT, TAG_DOCUMENTATION)
      .factory(springdocApplicationService::buildSpringdocWebfluxModuleWithSecurityJWT);
  }

  @Bean
  JHipsterModuleResource springdocMvcWithSecurityOAuth2Module(SpringdocApplicationService springdocApplicationService) {
    return JHipsterModuleResource
      .builder()
      .slug("springdoc-mvc-openapi-with-security-oauth2")
      .propertiesDefinition(buildPropertiesDefinition())
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add springdoc-openapi with Security OAuth2 for spring MVC"))
      .organization(springServerDependencyOrganization().addModuleDependency("spring-boot-oauth2").build())
      .tags(TAG_SERVER, TAG_SPRING, TAG_SPRING_BOOT, TAG_DOCUMENTATION, "authentication", "oauth2")
      .factory(springdocApplicationService::buildSpringdocMvcModuleWithSecurityOAuth2);
  }

  private JHipsterModulePropertiesDefinition buildPropertiesDefinition() {
    return JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build();
  }

  private JHipsterModuleOrganizationBuilder springServerDependencyOrganization() {
    return JHipsterModuleOrganization.builder().feature("springdoc").addFeatureDependency("spring-server");
  }

  private JHipsterModuleOrganizationBuilder webfluxDependencyOrganization() {
    return JHipsterModuleOrganization.builder().addModuleDependency("spring-boot-webflux-netty");
  }
}
