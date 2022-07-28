package tech.jhipster.lite.generator.buildtool.gradle.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.gradle.domain.GradleDomainService;
import tech.jhipster.lite.generator.buildtool.gradle.domain.GradleService;

@Configuration
public class GradleBeanConfiguration {

  @Bean
  public GradleService gradleService() {
    return new GradleDomainService();
  }
}
