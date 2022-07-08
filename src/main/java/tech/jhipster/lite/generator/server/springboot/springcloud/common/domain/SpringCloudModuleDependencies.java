package tech.jhipster.lite.generator.server.springboot.springcloud.common.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyType;

public final class SpringCloudModuleDependencies {

  public static final GroupId SPRING_CLOUD_GROUP = groupId("org.springframework.cloud");

  private SpringCloudModuleDependencies() {}

  public static JavaDependency springCloudDependenciesManagement() {
    return JavaDependency
      .builder()
      .groupId(SPRING_CLOUD_GROUP)
      .artifactId("spring-cloud-dependencies")
      .versionSlug("spring-cloud.version")
      .scope(JavaDependencyScope.IMPORT)
      .type(JavaDependencyType.POM)
      .build();
  }
}
