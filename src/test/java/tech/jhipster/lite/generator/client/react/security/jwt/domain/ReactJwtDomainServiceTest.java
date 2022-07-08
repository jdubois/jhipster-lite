package tech.jhipster.lite.generator.client.react.security.jwt.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.*;

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
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class ReactJwtDomainServiceTest {

  @InjectMocks
  ReactJwtDomainService reactJwtDomainService;

  @Mock
  NpmService npmService;

  @Mock
  ProjectRepository projectRepository;

  @Test
  void shouldAddJwtReact() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersionInReact(anyString())).thenReturn(Optional.of("0.0.0"));

    assertThatCode(() -> reactJwtDomainService.addLoginReact(project)).doesNotThrowAnyException();
  }

  @Test
  void shouldAddJwtDependencies() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersionInReact(anyString())).thenReturn(Optional.of("0.0.0"));

    reactJwtDomainService.addDependencies(project);

    verify(npmService, times(3)).addDependency(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldNotAddJwtDependencies() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersionInReact(anyString())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> reactJwtDomainService.addDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddJwtDevDependencies() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersionInReact(anyString())).thenReturn(Optional.of("0.0.0"));

    reactJwtDomainService.addDevDependencies(project);

    verify(npmService, times(1)).addDevDependency(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldNotAddJwtDevDependencies() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersionInReact(anyString())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> reactJwtDomainService.addDevDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddJwtFiles() {
    Project project = tmpProjectWithPackageJson();

    reactJwtDomainService.updateReactFilesForJWT(project);

    verify(projectRepository, times(3)).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }

  @Test
  void shouldAddReactJwtFiles() {
    Project project = tmpProjectWithPackageJson();

    reactJwtDomainService.addReactLoginFiles(project);

    verify(projectRepository).template(anyCollection());
  }
}
