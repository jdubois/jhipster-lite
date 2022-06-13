package tech.jhipster.lite.generator.client.tools.playwright.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProject;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectFilesAsserter;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class PlaywrightDomainServiceTest {

  @InjectMocks
  PlaywrightDomainService playwrightDomainService;

  @Mock
  NpmService npmService;

  @Mock
  ProjectRepository projectRepository;

  @Test
  void shouldAddPlaywright() {
    Project project = tmpProject();
    when(npmService.getVersionInCommon("@playwright/test")).thenReturn(Optional.of("0.0.0"));
    playwrightDomainService.init(project);

    verify(npmService, times(1)).addDevDependency(any(Project.class), anyString(), anyString());
    verify(npmService).addDevDependency(project, "@playwright/test", "0.0.0");

    verify(npmService, times(2)).addScript(any(Project.class), anyString(), anyString());
    verify(npmService).addScript(project, "e2e", "npx playwright test --headed");
    verify(npmService).addScript(project, "e2e:headless", "npx playwright test");

    verify(projectRepository).template(any(ProjectFile.class));
    verify(projectRepository, times(2)).template(ProjectFilesAsserter.filesCountArgument(1));
  }

  @Test
  void shouldNotInit() {
    Project project = tmpProject();

    assertThatThrownBy(() -> playwrightDomainService.init(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotAddDevDependencies() {
    Project project = tmpProject();

    assertThatThrownBy(() -> playwrightDomainService.addDevDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }
}
