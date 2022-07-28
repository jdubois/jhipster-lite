package tech.jhipster.lite.generator.client.angular.security.jwt.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.client.angular.common.domain.AngularCommonService;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFilesAsserter;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class AngularJwtDomainServiceTest {

  @InjectMocks
  AngularJwtDomainService angularJwtDomainService;

  @Mock
  NpmService npmService;

  @Mock
  AngularCommonService angularCommonService;

  @Mock
  ProjectRepository projectRepository;

  @Test
  void shouldAddJwtAngular() {
    Project project = tmpProjectWithPackageJson();

    assertThatCode(() -> angularJwtDomainService.addJwtAngular(project)).doesNotThrowAnyException();
  }

  @Test
  void shouldAddAngularJwtFiles() {
    Project project = tmpProjectWithPackageJson();

    angularJwtDomainService.addAngularJwtFiles(project);

    verify(projectRepository).template(ProjectFilesAsserter.filesCountArgument(17));
  }
}
