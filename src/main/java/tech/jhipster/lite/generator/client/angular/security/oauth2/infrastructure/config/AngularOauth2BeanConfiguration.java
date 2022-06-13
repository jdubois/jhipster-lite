package tech.jhipster.lite.generator.client.angular.security.oauth2.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.angular.common.domain.AngularCommonService;
import tech.jhipster.lite.generator.client.angular.security.oauth2.domain.AngularOauth2DomainService;
import tech.jhipster.lite.generator.client.angular.security.oauth2.domain.AngularOauth2Service;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@Configuration
public class AngularOauth2BeanConfiguration {

  private final ProjectRepository projectRepository;
  private final NpmService npmService;
  private final AngularCommonService angularCommonService;

  public AngularOauth2BeanConfiguration(
    ProjectRepository projectRepository,
    NpmService npmService,
    AngularCommonService angularCommonService
  ) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
    this.angularCommonService = angularCommonService;
  }

  @Bean
  public AngularOauth2Service angularOauth2Service() {
    return new AngularOauth2DomainService(projectRepository, npmService, angularCommonService);
  }
}
