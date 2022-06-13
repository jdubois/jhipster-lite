package tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_RESOURCES;
import static tech.jhipster.lite.generator.project.domain.Constants.POM_XML;

import java.util.List;
import tech.jhipster.lite.generator.project.domain.Project;

public class SpringbootActuatorAssert {

  public static void assertDependencies(Project project) {
    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter-actuator</artifactId>",
        "</dependency>"
      )
    );
  }

  public static void assertProperties(Project project) {
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "/config/application.properties"),
      List.of(
        "Spring Boot Actuator",
        "management.endpoints.web.base-path=/management",
        "management.endpoints.web.exposure.include=configprops, env, health, info, logfile, loggers, threaddump",
        "management.endpoint.health.probes.enabled=true",
        ""
      )
    );
  }
}
