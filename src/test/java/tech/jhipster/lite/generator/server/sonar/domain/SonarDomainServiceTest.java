package tech.jhipster.lite.generator.server.sonar.domain;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.*;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SonarDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @Mock
  BuildToolService buildToolService;

  @Mock
  DockerService dockerService;

  @InjectMocks
  SonarDomainService sonarDomainService;

  @Test
  void shouldAddSonarJavaBackend() {
    Project project = tmpProjectWithPomXml();
    when(buildToolService.getVersion(any(Project.class), anyString())).thenReturn(Optional.of("0.0.0"));
    when(dockerService.getImageNameWithVersion("sonarqube")).thenReturn(Optional.of("1.1.1"));

    sonarDomainService.addSonarJavaBackend(project);

    verify(buildToolService, times(2)).addProperty(any(Project.class), anyString(), anyString());
    verify(buildToolService).addPlugin(any(Project.class), any(Plugin.class));
    verify(buildToolService).addPluginManagement(any(Project.class), any(Plugin.class));

    verify(projectRepository, times(2)).template(any(ProjectFile.class));
  }

  @Test
  void shouldNotAddSonarJavaBackendWhenVersionNotFoundMavenDependency() {
    Project project = tmpProjectWithPomXml();

    Assertions.assertThatThrownBy(() -> sonarDomainService.addSonarJavaBackend(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotAddSonarJavaBackendWhenVersionNotFoundDockerImage() {
    Project project = tmpProjectWithPomXml();

    when(buildToolService.getVersion(any(Project.class), anyString())).thenReturn(Optional.of("0.0.0"));

    Assertions.assertThatThrownBy(() -> sonarDomainService.addSonarJavaBackend(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddSonarJavaBackendAndFrontend() {
    Project project = tmpProjectWithPomXml();
    when(buildToolService.getVersion(any(Project.class), anyString())).thenReturn(Optional.of("0.0.0"));
    when(dockerService.getImageNameWithVersion("sonarqube")).thenReturn(Optional.of("1.1.1"));

    sonarDomainService.addSonarJavaBackendAndFrontend(project);

    verify(buildToolService, times(2)).addProperty(any(Project.class), anyString(), anyString());
    verify(buildToolService).addPlugin(any(Project.class), any(Plugin.class));
    verify(buildToolService).addPluginManagement(any(Project.class), any(Plugin.class));

    verify(projectRepository, times(2)).template(any(ProjectFile.class));
  }

  @Test
  void shouldNotAddSonarJavaBackendAndFrontend() {
    Project project = tmpProjectWithPomXml();

    when(buildToolService.getVersion(any(Project.class), eq("properties-maven-plugin"))).thenReturn(Optional.of("0.0.0"));

    Assertions
      .assertThatThrownBy(() -> sonarDomainService.addSonarJavaBackendAndFrontend(project))
      .isExactlyInstanceOf(GeneratorException.class);
  }
}
