package tech.jhipster.lite.generator.client.angular.admin.health.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.client.angular.admin.health.application.AngularHealthAssert.*;
import static tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules.*;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.client.angular.core.application.AngularApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;

@IntegrationTest
class AngularHealthApplicationServiceIT {

  @Autowired
  AngularApplicationService angularApplicationService;

  @Autowired
  AngularHealthApplicationService angularHealthApplicationService;

  @Test
  void shouldAddHealthAngular() throws IOException {
    Project project = tmpProjectWithPackageJsonComplete();

    applyAngular(project);
    angularHealthApplicationService.addHealthAngular(project);

    assertAppHealth(project);
    assertUpdatedFiles(project);
  }
}
