package tech.jhipster.lite.generator.client.react.core.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.ProjectFilesAsserter.*;

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
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class ReactDomainServiceTest {

  @InjectMocks
  private ReactDomainService reactDomainService;

  @Mock
  private NpmService npmService;

  @Mock
  private ProjectRepository projectRepository;

  @Test
  void shouldAddReact() {
    Project project = tmpProject();
    when(npmService.getVersionInReact(anyString())).thenReturn(Optional.of("0.0.0"));

    reactDomainService.addReact(project);

    verify(npmService, times(2)).addDependency(any(Project.class), anyString(), anyString());
    verify(npmService, times(17)).addDevDependency(any(Project.class), anyString(), anyString());
    verify(npmService, times(6)).addScript(any(Project.class), anyString(), anyString());

    verify(projectRepository).add(filesCountArgument(3));
    verify(projectRepository).template(any(ProjectFile.class));
    verify(projectRepository).template(filesCountArgument(7));
  }

  @Test
  void shouldAddStyledReact() {
    Project project = tmpProject();
    when(npmService.getVersionInReact(anyString())).thenReturn(Optional.of("0.0.0"));

    reactDomainService.addStyledReact(project);

    verify(npmService, times(2)).addDependency(any(Project.class), anyString(), anyString());
    verify(npmService, times(17)).addDevDependency(any(Project.class), anyString(), anyString());
    verify(npmService, times(6)).addScript(any(Project.class), anyString(), anyString());

    verify(projectRepository).add(filesCountArgument(3));
    verify(projectRepository).template(filesCountArgument(7));
  }

  @Test
  void shouldNotAddReact() {
    Project project = tmpProject();

    assertThatThrownBy(() -> reactDomainService.addReact(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotAddDependencies() {
    Project project = tmpProject();

    assertThatThrownBy(() -> reactDomainService.addDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotAddDevDependencies() {
    Project project = tmpProject();

    assertThatThrownBy(() -> reactDomainService.addDevDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }
}
