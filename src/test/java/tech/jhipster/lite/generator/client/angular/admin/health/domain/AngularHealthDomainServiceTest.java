package tech.jhipster.lite.generator.client.angular.admin.health.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJson;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFilesAsserter;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class AngularHealthDomainServiceTest {

  @InjectMocks
  AngularHealthDomainService angularHealthDomainService;

  @Mock
  ProjectRepository projectRepository;

  @Test
  void shouldAddHealthFiles() {
    Project project = tmpProjectWithPackageJson();

    angularHealthDomainService.addHealthAngular(project);

    verify(projectRepository).template(ProjectFilesAsserter.filesCountArgument(17));
  }
}
