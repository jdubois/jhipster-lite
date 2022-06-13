package tech.jhipster.lite.generator.server.javatool.frontendmaven.domain;

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
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class FrontendMavenDomainServiceTest {

  @Mock
  BuildToolService buildToolService;

  @Mock
  ProjectRepository projectRepository;

  @InjectMocks
  FrontendMavenDomainService frontendMavenDomainService;

  @Test
  void shouldAddFrontendMavenPlugin() {
    Project project = tmpProjectWithPomXml();
    when(buildToolService.getVersion(any(Project.class), anyString())).thenReturn(Optional.of("0.0.0"));

    frontendMavenDomainService.addFrontendMavenPlugin(project);

    verify(buildToolService, times(5)).addProperty(any(Project.class), anyString(), anyString());
    verify(buildToolService, times(3)).addPlugin(any(Project.class), any(Plugin.class));
    verify(projectRepository, times(2)).template(any(ProjectFile.class));
  }

  @Test
  void shouldNotAddFrontendMavenPlugin() {
    Project project = tmpProjectWithPomXml();

    Assertions
      .assertThatThrownBy(() -> frontendMavenDomainService.addFrontendMavenPlugin(project))
      .isExactlyInstanceOf(GeneratorException.class);
  }
}
