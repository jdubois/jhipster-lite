package tech.jhipster.lite.generator.client.angular.security.jwt.application;

import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJsonComplete;
import static tech.jhipster.lite.generator.client.angular.security.jwt.application.AngularJwtAssert.assertAppJwt;
import static tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules.applyAngular;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.client.angular.core.application.AngularApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;

@IntegrationTest
class AngularJwtApplicationServiceIT {

  @Autowired
  AngularApplicationService angularApplicationService;

  @Autowired
  AngularJwtApplicationService angularJwtApplicationService;

  @Test
  void shouldAddJwtAngular() {
    Project project = tmpProjectWithPackageJsonComplete();

    applyAngular(project);
    angularJwtApplicationService.addJwtAngular(project);

    assertAppJwt(project);
  }
}
