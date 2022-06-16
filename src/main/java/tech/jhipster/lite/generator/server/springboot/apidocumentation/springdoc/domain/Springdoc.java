package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.domain;

import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.module.domain.javadependency.ArtifactId;
import tech.jhipster.lite.generator.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.generator.module.domain.javadependency.GroupId;

public class Springdoc {

  protected static final DependencyId WEBFLUX_DEPENDENCY_ID = new DependencyId(
    new GroupId("org.springframework.boot"),
    new ArtifactId("spring-boot-starter-webflux")
  );

  private Springdoc() {}

  public static Dependency springdocDependencyForMvc() {
    return Dependency
      .builder()
      .groupId("org.springdoc")
      .artifactId("springdoc-openapi-ui")
      .version("\\${springdoc-openapi-ui.version}")
      .build();
  }

  public static Dependency springdocDependencyForWebflux() {
    return Dependency
      .builder()
      .groupId("org.springdoc")
      .artifactId("springdoc-openapi-webflux-ui")
      .version("\\${springdoc-openapi-ui.version}")
      .build();
  }
}
