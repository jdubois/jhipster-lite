package tech.jhipster.lite.generator.server.springboot.mvc.web.domain;

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
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectFilesAsserter;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SpringBootMvcDomainServiceTest {

  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private BuildToolService buildToolService;

  @Mock
  private SpringBootCommonService springBootCommonService;

  @InjectMocks
  private SpringBootMvcDomainService springBootMvcDomainService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPomXml();
    when(buildToolService.getVersion(project, "problem-spring")).thenReturn(Optional.of("0.0.0"));

    springBootMvcDomainService.init(project);

    verify(buildToolService, times(3)).addDependency(any(Project.class), any(Dependency.class));

    verify(springBootCommonService, times(9)).addPropertiesComment(any(Project.class), anyString());
    verify(springBootCommonService, times(3)).addProperties(any(Project.class), anyString(), any());
    verify(springBootCommonService, times(9)).addPropertiesTest(any(Project.class), anyString(), any());
    verify(springBootCommonService, times(3)).addPropertiesNewLine(any(Project.class));
    verify(springBootCommonService).addLogger(any(Project.class), anyString(), any(Level.class));
    verify(springBootCommonService).addLoggerTest(any(Project.class), anyString(), any(Level.class));

    verify(projectRepository, times(13)).template(any(ProjectFile.class));
    verify(projectRepository).template(ProjectFilesAsserter.filesCountArgument(2));
  }

  @Test
  void shouldAddSpringBootUndertow() {
    Project project = tmpProjectWithPomXml();
    when(buildToolService.getVersion(project, "problem-spring")).thenReturn(Optional.of("0.0.0"));

    springBootMvcDomainService.addSpringBootUndertow(project);

    verify(buildToolService, times(3)).addDependency(any(Project.class), any(Dependency.class));

    verify(springBootCommonService, times(9)).addPropertiesComment(any(Project.class), anyString());
    verify(springBootCommonService, times(3)).addProperties(any(Project.class), anyString(), any());
    verify(springBootCommonService, times(9)).addPropertiesTest(any(Project.class), anyString(), any());
    verify(springBootCommonService, times(3)).addPropertiesNewLine(any(Project.class));
    verify(springBootCommonService).addLogger(any(Project.class), anyString(), any(Level.class));
    verify(springBootCommonService).addLoggerTest(any(Project.class), anyString(), any(Level.class));

    verify(projectRepository, times(13)).template(any(ProjectFile.class));
    verify(projectRepository).template(ProjectFilesAsserter.filesCountArgument(2));
  }

  @Test
  void shouldNotAddExceptionHandler() {
    Project project = tmpProjectWithPomXml();

    assertThatThrownBy(() -> springBootMvcDomainService.addExceptionHandler(project)).isExactlyInstanceOf(GeneratorException.class);
  }
}
