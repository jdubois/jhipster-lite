package tech.jhipster.lite.generator.client.angular.security.jwt.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.angular.common.domain.AngularCommonService;
import tech.jhipster.lite.generator.client.angular.security.jwt.domain.AngularJwtDomainService;
import tech.jhipster.lite.generator.client.angular.security.jwt.domain.AngularJwtService;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@Configuration
public class AngularJwtBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final AngularCommonService angularCommonService;

  public AngularJwtBeanConfiguration(ProjectRepository projectRepository, AngularCommonService angularCommonService) {
    this.projectRepository = projectRepository;
    this.angularCommonService = angularCommonService;
  }

  @Bean
  public AngularJwtService angularJwtService() {
    return new AngularJwtDomainService(projectRepository, angularCommonService);
  }
}
