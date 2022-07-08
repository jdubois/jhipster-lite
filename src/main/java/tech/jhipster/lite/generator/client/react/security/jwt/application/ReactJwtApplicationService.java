package tech.jhipster.lite.generator.client.react.security.jwt.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.react.security.jwt.domain.ReactJwtService;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public class ReactJwtApplicationService {

  private final ReactJwtService reactJwtService;

  public ReactJwtApplicationService(final ReactJwtService reactJwtService) {
    this.reactJwtService = reactJwtService;
  }

  public void addLoginReact(final Project project) {
    reactJwtService.addLoginReact(project);
  }
}
