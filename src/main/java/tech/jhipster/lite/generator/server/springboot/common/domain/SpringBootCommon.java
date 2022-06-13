package tech.jhipster.lite.generator.server.springboot.common.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.TECHNICAL_INFRASTRUCTURE_PRIMARY;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

public class SpringBootCommon {

  public static final String TECHNICAL_INFRASTRUCTURE_PRIMARY_EXCEPTION = getPath(TECHNICAL_INFRASTRUCTURE_PRIMARY, "exception");

  public static Dependency testContainersDependency(String artifactId) {
    Assert.notBlank("artifactId", artifactId);
    return Dependency.builder().groupId("org.testcontainers").artifactId(artifactId).scope("test").build();
  }

  private SpringBootCommon() {}
}
