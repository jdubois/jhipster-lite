package tech.jhipster.lite.generator.buildtool.maven.infrastructure.primary;

import static tech.jhipster.lite.generator.JHLiteFeatureSlug.*;
import static tech.jhipster.lite.generator.JHLiteModuleSlug.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class MavenModuleConfiguration {

  @Bean
  JHipsterModuleResource mavenModule(MavenApplicationService maven) {
    return JHipsterModuleResource
      .builder()
      .slug(MAVEN_JAVA)
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addProjectName().build())
      .apiDoc("Build Tool", "Init Maven project with pom.xml and wrapper")
      .organization(JHipsterModuleOrganization.builder().feature(JAVA_BUILD_TOOL).addDependency(INIT).build())
      .tags("buildtool", "test")
      .factory(maven::buildMavenModule);
  }

  @Bean
  JHipsterModuleResource mavenWrapperModule(MavenApplicationService maven) {
    return JHipsterModuleResource
      .builder()
      .slug(MAVEN_WRAPPER)
      .withoutProperties()
      .apiDoc("Build Tool", "Add maven wrapper")
      .standalone()
      .tags("buildtool", "test")
      .factory(maven::buildMavenWrapperModule);
  }
}
