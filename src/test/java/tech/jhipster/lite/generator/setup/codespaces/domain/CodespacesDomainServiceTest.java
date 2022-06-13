package tech.jhipster.lite.generator.setup.codespaces.domain;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class CodespacesDomainServiceTest {

  @Mock
  private ProjectRepository projectRepository;

  @InjectMocks
  private CodespacesDomainService codespacesDomainService;

  @Test
  void shouldAddCodespacesFiles() {
    Project project = tmpProjectWithPackageJson();

    codespacesDomainService.init(project);
    verify(projectRepository).template(any(ProjectFile.class));
    verify(projectRepository).add(any(ProjectFile.class));
  }
}
