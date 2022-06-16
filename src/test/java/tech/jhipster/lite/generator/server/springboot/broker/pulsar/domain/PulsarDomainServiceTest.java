package tech.jhipster.lite.generator.server.springboot.broker.pulsar.domain;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.docker.domain.DockerImage;
import tech.jhipster.lite.generator.docker.domain.DockerImages;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

@UnitTest
@ExtendWith(MockitoExtension.class)
class PulsarDomainServiceTest {

  @Mock
  private BuildToolService buildToolService;

  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private SpringBootCommonService springBootCommonService;

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private PulsarDomainService pulsarDomainService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPomXml();
    when(dockerImages.get(anyString())).thenReturn(new DockerImage("dummy", "1"));

    pulsarDomainService.init(project);

    verify(buildToolService, times(1)).addVersionPropertyAndDependency(eq(project), eq("pulsar"), any(Dependency.class));
    verify(buildToolService, times(1)).addVersionPropertyAndDependency(eq(project), eq("testcontainers"), any(Dependency.class));
    verify(dockerImages, times(1)).get(anyString());
    verify(projectRepository, times(5)).template(any(ProjectFile.class));
    verify(springBootCommonService, times(1)).addProperties(eq(project), anyString(), any());
    verify(springBootCommonService, times(1)).addPropertiesComment(eq(project), anyString());
    verify(springBootCommonService, times(4)).addPropertiesTest(eq(project), anyString(), any());
    verify(springBootCommonService, times(1)).addPropertiesTestComment(eq(project), anyString());
    verify(springBootCommonService).updateIntegrationTestAnnotation(eq(project), anyString());
  }
}
