package tech.jhipster.lite.generator.client.angular.security.oauth2.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.angular.security.oauth2.domain.AngularOauth2Service;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public class AngularOauth2ApplicationService {

  private final AngularOauth2Service angularOauth2Service;

  public AngularOauth2ApplicationService(AngularOauth2Service angularOauth2Service) {
    this.angularOauth2Service = angularOauth2Service;
  }

  public void addOauth2(Project project) {
    angularOauth2Service.addOauth2(project);
  }
}
