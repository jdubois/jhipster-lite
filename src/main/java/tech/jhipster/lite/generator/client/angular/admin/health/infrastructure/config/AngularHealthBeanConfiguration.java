package tech.jhipster.lite.generator.client.angular.admin.health.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.angular.admin.health.domain.AngularHealthDomainService;
import tech.jhipster.lite.generator.client.angular.admin.health.domain.AngularHealthService;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@Configuration
public class AngularHealthBeanConfiguration {

  private final ProjectRepository projectRepository;

  public AngularHealthBeanConfiguration(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Bean
  public AngularHealthService angularHealthService() {
    return new AngularHealthDomainService(projectRepository);
  }
}
