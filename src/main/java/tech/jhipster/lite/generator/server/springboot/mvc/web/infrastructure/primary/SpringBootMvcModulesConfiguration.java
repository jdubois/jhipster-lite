package tech.jhipster.lite.generator.server.springboot.mvc.web.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class SpringBootMvcModulesConfiguration {

  private static final String SPRING_BOOT = "spring-boot";

  @Bean
  JHipsterModuleResource springBootTomcatMvcModule(SpringBootMvcApplicationService springBootMvc) {
    return JHipsterModuleResource
      .builder()
      .slug("spring-boot-tomcat")
      .propertiesDefinition(properties())
      .apiDoc("Spring Boot - MVC", "Add Spring Boot MVC with Tomcat")
      .organization(organization())
      .tags("server", "spring", SPRING_BOOT, "mvc", "web", "tomcat")
      .factory(springBootMvc::buildTomcatModule);
  }

  @Bean
  JHipsterModuleResource springBootUndertowMvcModule(SpringBootMvcApplicationService springBootMvc) {
    return JHipsterModuleResource
      .builder()
      .slug("spring-boot-undertow")
      .propertiesDefinition(properties())
      .apiDoc("Spring Boot - MVC", "Add Spring Boot MVC with Undertow")
      .organization(organization())
      .tags("server", "spring", SPRING_BOOT, "mvc", "web", "undertow")
      .factory(springBootMvc::buildUndertowModule);
  }

  private JHipsterModulePropertiesDefinition properties() {
    return JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addServerPort().build();
  }

  private JHipsterModuleOrganization organization() {
    return JHipsterModuleOrganization.builder().feature("spring-server").addModuleDependency(SPRING_BOOT).build();
  }
}
