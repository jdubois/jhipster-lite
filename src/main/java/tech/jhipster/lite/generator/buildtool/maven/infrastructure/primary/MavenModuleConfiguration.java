package tech.jhipster.lite.generator.buildtool.maven.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class MavenModuleConfiguration {

  @Bean
  JHipsterModuleResource mavenModule(MavenApplicationService maven) {
    return JHipsterModuleResource
      .builder()
      .slug("maven-java")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addProjectBaseName().addProjectName().build())
      .apiDoc(new JHipsterModuleApiDoc("Build Tool", "Init Maven project with pom.xml and wrapper"))
      .organization(JHipsterModuleOrganization.builder().feature("java-build-tool").addModuleDependency("init").build())
      .tags("buildtool", "test")
      .factory(maven::buildModule);
  }
}
