package tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.domain;

import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

public class SpringBootActuator {

  private SpringBootActuator() {}

  public static Dependency springBootActuatorDependency() {
    return Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-actuator").build();
  }
}
