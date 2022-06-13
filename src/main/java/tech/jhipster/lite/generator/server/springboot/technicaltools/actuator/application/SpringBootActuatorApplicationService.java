package tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.domain.SpringBootActuatorService;

@Service
public class SpringBootActuatorApplicationService {

  private final SpringBootActuatorService springBootActuatorService;

  public SpringBootActuatorApplicationService(SpringBootActuatorService springBootActuatorService) {
    this.springBootActuatorService = springBootActuatorService;
  }

  public void addActuator(Project project) {
    springBootActuatorService.addActuator(project);
  }
}
