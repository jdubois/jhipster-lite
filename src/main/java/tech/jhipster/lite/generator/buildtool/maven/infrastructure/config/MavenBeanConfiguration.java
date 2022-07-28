package tech.jhipster.lite.generator.buildtool.maven.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenDomainService;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenService;

@Configuration
public class MavenBeanConfiguration {

  @Bean
  public MavenService mavenService() {
    return new MavenDomainService();
  }
}
