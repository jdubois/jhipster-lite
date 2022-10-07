package tech.jhipster.lite.generator.server.springboot.database.mysql.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.database.mysql.application.MySQLApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class MySQLModuleConfiguration {

  @Bean
  JHipsterModuleResource mySQLModule(MySQLApplicationService applicationService) {
    return JHipsterModuleResource
      .builder()
      .slug(MYSQL)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().addProjectBaseName().build())
      .apiDoc("Spring Boot - Database", "Add MySQL to project")
      .organization(JHipsterModuleOrganization.builder().feature(JPA_PERSISTENCE).addDependency(SPRING_BOOT).build())
      .tags("server", "spring", "spring-boot", "database")
      .factory(applicationService::build);
  }
}
