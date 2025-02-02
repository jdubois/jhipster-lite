package tech.jhipster.lite.generator.server.springboot.mvc.dummy.mongopersistence.infrastructure.primary;

import static tech.jhipster.lite.generator.slug.domain.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.slug.domain.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.mvc.dummy.mongopersistence.application.DummyMongoDBPersistenceApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class DummyMongoDBPersistenceModuleConfiguration {

  @Bean
  JHipsterModuleResource dummyMongoDBModule(DummyMongoDBPersistenceApplicationService dummyMongoDBPersistence) {
    return JHipsterModuleResource.builder()
      .slug(DUMMY_MONGODB_PERSISTENCE)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().build())
      .apiDoc("Spring Boot - MVC", "Add MongoDB persistence for dummy feature")
      .organization(
        JHipsterModuleOrganization.builder().feature(DUMMY_PERSISTENCE).addDependency(DUMMY_FEATURE).addDependency(MONGOCK).build()
      )
      .tags("server")
      .factory(dummyMongoDBPersistence::buildModule);
  }
}
