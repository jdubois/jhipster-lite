package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.domain.SpringdocService;

@Service
public class SpringdocApplicationService {

  private final SpringdocService springdocService;

  public SpringdocApplicationService(SpringdocService springdocService) {
    this.springdocService = springdocService;
  }

  public void init(Project project) {
    springdocService.init(project);
  }

  public void initWithSecurityJWT(Project project) {
    springdocService.initWithSecurityJWT(project);
  }
}
