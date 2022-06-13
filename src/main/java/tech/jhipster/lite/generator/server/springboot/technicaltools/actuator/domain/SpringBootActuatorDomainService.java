package tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.domain;

import static tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.domain.SpringBootActuator.springBootActuatorDependency;

import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

public class SpringBootActuatorDomainService implements SpringBootActuatorService {

  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;

  public SpringBootActuatorDomainService(BuildToolService buildToolService, SpringBootCommonService springBootCommonService) {
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
  }

  @Override
  public void addActuator(Project project) {
    buildToolService.addDependency(project, springBootActuatorDependency());

    springBootCommonService.addPropertiesComment(project, "Spring Boot Actuator");
    springBootCommonService.addProperties(project, "management.endpoints.web.base-path", "/management");
    springBootCommonService.addProperties(
      project,
      "management.endpoints.web.exposure.include",
      "configprops, env, health, info, logfile, loggers, threaddump"
    );
    springBootCommonService.addProperties(project, "management.endpoint.health.probes.enabled", "true");
    springBootCommonService.addPropertiesNewLine(project);
  }
}
