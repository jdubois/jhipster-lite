package tech.jhipster.lite.generator.buildtool.generic.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolDomainService;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.gradle.domain.GradleService;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenService;

@Configuration
public class BuildToolBeanConfiguration {

  private final MavenService mavenService;
  private final GradleService gradleService;

  public BuildToolBeanConfiguration(MavenService mavenService, GradleService gradleService) {
    this.mavenService = mavenService;
    this.gradleService = gradleService;
  }

  @Bean
  public BuildToolService buildToolService() {
    return new BuildToolDomainService(mavenService, gradleService);
  }
}
