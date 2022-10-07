package tech.jhipster.lite.generator.server.springboot.async.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.async.application.SpringBootAsyncApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class SpringBootAsyncModuleConfiguration {

  @Bean
  JHipsterModuleResource springBootAsync(SpringBootAsyncApplicationService springBootAsync) {
    return JHipsterModuleResource
      .builder()
      .slug(SPRING_BOOT_ASYNC)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().build())
      .apiDoc("Spring Boot - Tools", "Add asynchronous execution and scheduling configuration")
      .organization(JHipsterModuleOrganization.builder().addDependency(SPRING_BOOT).build())
      .tags("server", "spring", "spring-boot", "async")
      .factory(springBootAsync::buildModule);
  }
}
