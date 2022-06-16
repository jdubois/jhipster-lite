package tech.jhipster.lite.generator.client.angular.admin.health.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.angular.admin.health.domain.AngularHealthService;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public class AngularHealthApplicationService {

  private final AngularHealthService angularHealthService;

  public AngularHealthApplicationService(AngularHealthService angularHealthService) {
    this.angularHealthService = angularHealthService;
  }

  public void addHealthAngular(Project project) {
    angularHealthService.addHealthAngular(project);
  }
}
