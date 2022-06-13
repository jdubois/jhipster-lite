package tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.*;

import java.util.Optional;
import org.eclipse.jgit.api.errors.GitAPIException;
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
import tech.jhipster.lite.generator.project.infrastructure.secondary.GitUtils;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.mvc.security.common.domain.CommonSecurityService;

@UnitTest
@ExtendWith(MockitoExtension.class)
class JwtSecurityDomainServiceTest {

  @Mock
  SpringBootCommonService springBootCommonService;

  @Mock
  ProjectRepository projectRepository;

  @Mock
  BuildToolService buildToolService;

  @Mock
  CommonSecurityService commonSecurityService;

  @InjectMocks
  JwtSecurityDomainService jwtSecurityDomainService;

  @Test
  void shouldInit() throws GitAPIException {
    Project project = tmpProjectWithPomXml();
    GitUtils.init(project.getFolder());
    when(buildToolService.getVersion(project, "jjwt")).thenReturn(Optional.of("0.0.0"));

    jwtSecurityDomainService.init(project);

    verify(buildToolService).addProperty(any(Project.class), anyString(), anyString());
    verify(buildToolService, times(5)).addDependency(any(Project.class), any(Dependency.class));

    verify(projectRepository).template(ProjectFilesAsserter.filesCountArgument(8));
    verify(projectRepository).template(ProjectFilesAsserter.filesCountArgument(4));

    verify(springBootCommonService, times(3)).addProperties(any(Project.class), anyString(), any());
    verify(springBootCommonService, times(3)).addPropertiesTest(any(Project.class), anyString(), any());

    verify(springBootCommonService).addLogger(any(Project.class), anyString(), any());
    verify(springBootCommonService).addLoggerTest(any(Project.class), anyString(), any());

    // The 4 replaces, managed by commonSecurityService
    verify(commonSecurityService).updateExceptionTranslator(project);
    verify(commonSecurityService).updateIntegrationTestWithMockUser(project);
  }

  @Test
  void shouldNotaddPropertyAndDependency() throws GitAPIException {
    Project project = tmpProjectWithPomXml();
    GitUtils.init(project.getFolder());

    assertThatThrownBy(() -> jwtSecurityDomainService.init(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddBasicAuth() {
    Project project = tmpProjectWithPomXml();

    jwtSecurityDomainService.addBasicAuth(project);

    verify(projectRepository, times(6)).template(any(ProjectFile.class));
    verify(springBootCommonService, times(3)).addProperties(any(Project.class), anyString(), any());
    verify(springBootCommonService, times(3)).addPropertiesTest(any(Project.class), anyString(), any());
  }
}
