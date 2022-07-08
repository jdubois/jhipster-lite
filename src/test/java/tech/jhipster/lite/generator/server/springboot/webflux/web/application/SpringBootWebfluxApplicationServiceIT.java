package tech.jhipster.lite.generator.server.springboot.webflux.web.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;
import static tech.jhipster.lite.generator.server.springboot.webflux.web.application.SpringbootWebfluxAssert.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
class SpringBootWebfluxApplicationServiceIT {

  @Autowired
  private SpringBootWebfluxApplicationService springBootWebfluxApplicationService;

  @Autowired
  private MavenApplicationService mavenApplicationService;

  @Autowired
  private SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    TestJHipsterModules.applyInit(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootWebfluxApplicationService.init(project);

    assertDependencies(project);
    assertProperties(project);
    assertExceptionHandlerDependencies(project);
    assertExceptionHandlerProperties(project);
    assertExceptionHandlerFiles(project);
  }

  @Test
  void shouldAddSpringBootWebflux() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    TestJHipsterModules.applyInit(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootWebfluxApplicationService.addSpringBootWebflux(project);

    assertDependencies(project);
    assertProperties(project);
  }

  @Test
  void shouldAddExceptionHandler() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    TestJHipsterModules.applyInit(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootWebfluxApplicationService.addExceptionHandler(project);

    assertExceptionHandlerDependencies(project);
    assertExceptionHandlerProperties(project);
    assertExceptionHandlerFiles(project);
  }
}
