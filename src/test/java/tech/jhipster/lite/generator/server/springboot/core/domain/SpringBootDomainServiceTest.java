package tech.jhipster.lite.generator.server.springboot.core.domain;

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
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SpringBootDomainServiceTest {

  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private BuildToolService buildToolService;

  @InjectMocks
  private SpringBootDomainService springBootDomainService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPomXml();
    when(buildToolService.getVersion(project, "spring-boot")).thenReturn(Optional.of("0.0.0"));

    springBootDomainService.init(project);

    verify(buildToolService).addDependencyManagement(any(Project.class), any(Dependency.class));
    verify(buildToolService, times(4)).addDependency(any(Project.class), any(Dependency.class));
    verify(buildToolService).addPlugin(any(Project.class), any(Plugin.class));

    verify(projectRepository, times(8)).template(any(ProjectFile.class));
  }

  @Test
  void shouldAddSpringBootDependenciesBOM() {
    Project project = tmpProjectWithPomXml();
    when(buildToolService.getVersion(project, "spring-boot")).thenReturn(Optional.of("0.0.0"));

    springBootDomainService.addSpringBootDependenciesBOM(project);

    verify(buildToolService).addDependencyManagement(any(Project.class), any(Dependency.class));
  }

  @Test
  void shouldNotAddSpringBootDependenciesBOMParent() {
    Project project = tmpProjectWithPomXml();

    assertThatThrownBy(() -> springBootDomainService.addSpringBootDependenciesBOM(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddSpringBootDependencies() {
    Project project = tmpProjectWithPomXml();

    springBootDomainService.addSpringBootDependencies(project);

    verify(buildToolService, times(4)).addDependency(any(Project.class), any(Dependency.class));
  }

  @Test
  void shouldAddSpringBootPluginManagement() {
    Project project = tmpProjectWithPomXml();
    when(buildToolService.getVersion(project, "spring-boot")).thenReturn(Optional.of("0.0.0"));

    springBootDomainService.addSpringBootMavenPluginManagement(project);

    verify(buildToolService).addPluginManagement(any(Project.class), any(Plugin.class));
  }

  @Test
  void shouldNotAddSpringBootPluginManagement() {
    Project project = tmpProjectWithPomXml();

    assertThatThrownBy(() -> springBootDomainService.addSpringBootMavenPluginManagement(project))
      .isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddSpringBootPlugin() {
    Project project = tmpProjectWithPomXml();

    springBootDomainService.addSpringBootMavenPlugin(project);

    verify(buildToolService).addPlugin(any(Project.class), any(Plugin.class));
  }
}
