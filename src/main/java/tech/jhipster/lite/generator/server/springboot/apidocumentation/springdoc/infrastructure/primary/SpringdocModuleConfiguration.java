package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.application.SpringdocApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class SpringdocModuleConfiguration {

  private static final String SPRINGDOC_API_URL = "/api/servers/spring-boot/api-documentations/springdoc";

  public static final String TAG = "Spring Boot - API Documentation";
  public static final String TAG_SERVER = "server";
  public static final String TAG_SPRING = "spring";
  public static final String TAG_SPRING_BOOT = "spring-boot";
  public static final String TAG_DOCUMENTATION = "documentation";

  @Bean
  JHipsterModuleResource springdocModule(SpringdocApplicationService springdocApplicationService) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl(SPRINGDOC_API_URL + "/init")
      .slug("springdoc-openapi")
      .propertiesDefinition(buildPropertiesDefinition())
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add springdoc-openapi"))
      .tags(TAG_SERVER, TAG_SPRING, TAG_SPRING_BOOT, TAG_DOCUMENTATION)
      .factory(springdocApplicationService::buildSpringdocModule);
  }

  @Bean
  JHipsterModuleResource springdocWithSecurityJwtModule(SpringdocApplicationService springdocApplicationService) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl(SPRINGDOC_API_URL + "/init-with-security-jwt")
      .slug("springdoc-openapi-with-security-jwt")
      .propertiesDefinition(buildPropertiesDefinition())
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add springdoc-openapi with Security JWT"))
      .tags(TAG_SERVER, TAG_SPRING, TAG_SPRING_BOOT, TAG_DOCUMENTATION)
      .factory(springdocApplicationService::buildSpringdocModuleWithSecurityJWT);
  }

  @Bean
  JHipsterModuleResource springdocWithSecurityOAuth2Module(SpringdocApplicationService springdocApplicationService) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl(SPRINGDOC_API_URL + "/init-with-security-oauth2")
      .slug("springdoc-openapi-with-security-oauth2")
      .propertiesDefinition(buildPropertiesDefinition())
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add springdoc-openapi with Security OAuth2"))
      .tags(TAG_SERVER, TAG_SPRING, TAG_SPRING_BOOT, TAG_DOCUMENTATION, "authentication", "oauth2")
      .factory(springdocApplicationService::buildSpringdocModuleWithSecurityOAuth2);
  }

  private JHipsterModulePropertiesDefinition buildPropertiesDefinition() {
    return JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addIndentation().build();
  }
}
