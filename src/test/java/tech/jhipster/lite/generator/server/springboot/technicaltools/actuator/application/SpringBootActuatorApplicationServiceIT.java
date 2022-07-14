package tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.application;

import static tech.jhipster.lite.TestUtils.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
class SpringBootActuatorApplicationServiceIT {

  @Autowired
  private SpringBootActuatorApplicationService springBootActuatorApplicationService;

  @Test
  void shouldAddSpringBootActuator() {
    Project project = tmpProject();
    TestJHipsterModules.applyInit(project);
    TestJHipsterModules.applyMaven(project);
    TestJHipsterModules.applySpringBootCore(project);

    springBootActuatorApplicationService.addActuator(project);

    SpringbootActuatorAssert.assertDependencies(project);
    SpringbootActuatorAssert.assertProperties(project);
  }
}
