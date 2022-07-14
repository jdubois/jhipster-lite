package tech.jhipster.lite.generator.client.svelte.core.domain;

import static org.assertj.core.api.Assertions.*;
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
class SvelteDomainServiceTest {

  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private NpmService npmService;

  @InjectMocks
  private SvelteDomainService svelteDomainService;

  @Test
  void shouldAddSvelte() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    assertThatCode(() -> svelteDomainService.addSvelte(project)).doesNotThrowAnyException();
  }

  @Test
  void shouldAddDependencies() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    svelteDomainService.addDependencies(project);

    verify(npmService, times(1)).addDependency(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldNotAddDependencies() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> svelteDomainService.addDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddDevDependencies() {
    Project project = tmpProjectWithPackageJson();
    when(npmService.getVersion(anyString(), anyString())).thenReturn(Optional.of("0.0.0"));

    svelteDomainService.addDevDependencies(project);

    verify(npmService, times(27)).addDevDependency(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldNotAddDevDependencies() {
    Project project = tmpProjectWithPackageJson();

    assertThatThrownBy(() -> svelteDomainService.addDevDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddScripts() {
    Project project = tmpProjectWithPackageJson();

    svelteDomainService.addScripts(project);

    verify(npmService, times(10)).addScript(any(Project.class), anyString(), anyString());
  }

  @Test
  void shouldAddSvelteConfigFiles() {
    Project project = tmpProjectWithPackageJson();

    svelteDomainService.addSvelteConfigFile(project);

    verify(projectRepository).add(filesCountArgument(5));
  }

  @Test
  void shouldAddRootFiles() {
    Project project = tmpProject();

    svelteDomainService.addRootFiles(project);

    verify(projectRepository, times(3)).template(any(ProjectFile.class));
  }

  @Test
  void shouldAddAppFiles() {
    Project project = tmpProject();

    svelteDomainService.addAppFiles(project);

    verify(projectRepository, times(2)).template(any(ProjectFile.class));
  }

  @Test
  void shouldRenameJhipsterFiles() {
    Project project = tmpProject();

    svelteDomainService.renameJhipsterFiles(project);

    verify(projectRepository, times(1)).rename(any(Project.class), anyString(), anyString(), anyString());
  }
}
