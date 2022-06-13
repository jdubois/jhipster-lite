package tech.jhipster.lite.generator.ci.github.actions.domain;

import static org.assertj.core.api.Assertions.*;
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
class GitHubActionsDomainServiceTest {

  @Mock
  private ProjectRepository projectRepository;

  @InjectMocks
  private GitHubActionsDomainService gitHubActionsDomainService;

  @Test
  void shouldAddGitHubActionsForGradle() {
    Project project = tmpProjectWithBuildGradle();

    assertThatCode(() -> gitHubActionsDomainService.addGitHubActions(project)).doesNotThrowAnyException();

    verify(projectRepository, times(2)).template(any(ProjectFile.class));
  }

  @Test
  void shouldAddGitHubActionsForMaven() {
    Project project = tmpProjectWithPomXml();

    assertThatCode(() -> gitHubActionsDomainService.addGitHubActions(project)).doesNotThrowAnyException();

    verify(projectRepository, times(2)).template(any(ProjectFile.class));
  }
}
