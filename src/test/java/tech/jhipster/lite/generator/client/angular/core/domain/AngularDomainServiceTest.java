package tech.jhipster.lite.generator.client.angular.core.domain;

import static org.assertj.core.api.Assertions.*;
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
import tech.jhipster.lite.generator.project.domain.ProjectFilesAsserter;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class AngularDomainServiceTest {

  @InjectMocks
  AngularDomainService angularDomainService;

  @Mock
  NpmService npmService;

  @Mock
  ProjectRepository projectRepository;

  @Test
  void shouldAddAngular() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    assertThatCode(() -> angularDomainService.addAngular(project)).doesNotThrowAnyException();
  }

  @Test
  void shouldNotAddAngular() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> angularDomainService.addAngular(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddDependencies() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    angularDomainService.addDependencies(project);

    verify(npmService, times(13)).addDependency(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldNotAddDependencies() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> angularDomainService.addDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddDevDependencies() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    angularDomainService.addDevDependencies(project);

    verify(npmService, times(12)).addDevDependency(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldNotAddDevDependencies() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> angularDomainService.addDevDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddScripts() {
    Project project = tmpProjectWithPackageJson();

    angularDomainService.addScripts(project);

    verify(npmService, times(5)).addScript(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddFiles() {
    Project project = tmpProjectWithPackageJson();

    angularDomainService.addFiles(project);

    verify(projectRepository).add(filesCountArgument(6));
  }

  @Test
  void shouldAddAngularFiles() {
    Project project = tmpProjectWithPackageJson();

    angularDomainService.addAngularFiles(project);

    verify(projectRepository).template(ProjectFilesAsserter.filesCountArgument(15));
  }
}
