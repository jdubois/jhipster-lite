package tech.jhipster.lite.generator.server.springboot.cache.common.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;
import static tech.jhipster.lite.generator.server.springboot.cache.common.application.SpringBootCacheAssert.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
class SpringBootCacheApplicationServiceIT {

  @Autowired
  private SpringBootCacheApplicationService springBootCacheApplicationService;

  @Test
  void shouldAddDependencies() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.baz");
    TestJHipsterModules.applyInit(project);
    TestJHipsterModules.applyMaven(project);

    springBootCacheApplicationService.addDependencies(project);

    assertDependencies(project);
  }

  @Test
  void shouldEnableCaching() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.baz");
    TestJHipsterModules.applyInit(project);
    TestJHipsterModules.applyMaven(project);
    TestJHipsterModules.applySpringBootCore(project);

    springBootCacheApplicationService.addEnableCaching(project);

    assertEnableCaching(project);
  }
}
