package tech.jhipster.lite.generator.client.react.security.jwt.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.react.security.jwt.domain.ReactJwtDomainService;
import tech.jhipster.lite.generator.client.react.security.jwt.domain.ReactJwtService;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@Configuration
public class ReactJwtBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final NpmService npmService;

  public ReactJwtBeanConfiguration(ProjectRepository projectRepository, NpmService npmService) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
  }

  @Bean
  public ReactJwtService reactJwtService() {
    return new ReactJwtDomainService(projectRepository, npmService);
  }
}
