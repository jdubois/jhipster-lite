package tech.jhipster.lite.generator.client.angular.security.oauth2.application;

import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJsonComplete;
import static tech.jhipster.lite.generator.client.angular.security.oauth2.application.AngularOauth2Assert.assertAddedFiles;
import static tech.jhipster.lite.generator.client.angular.security.oauth2.application.AngularOauth2Assert.assertDependencies;
import static tech.jhipster.lite.generator.client.angular.security.oauth2.application.AngularOauth2Assert.assertUpdatedFiles;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.client.angular.core.application.AngularApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;

@IntegrationTest
class AngularOauth2ApplicationServiceIT {

  @Autowired
  AngularApplicationService angularApplicationService;

  @Autowired
  AngularOauth2ApplicationService angularOauth2ApplicationService;

  @Test
  void shouldAddOauth2() throws IOException {
    // Given
    Project project = tmpProjectWithPackageJsonComplete();
    angularApplicationService.addAngular(project);

    // When
    angularOauth2ApplicationService.addOauth2(project);

    // Then
    assertDependencies(project);
    assertAddedFiles(project);
    assertUpdatedFiles(project);
  }
}
