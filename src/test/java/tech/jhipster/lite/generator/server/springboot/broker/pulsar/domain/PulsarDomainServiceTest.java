package tech.jhipster.lite.generator.server.springboot.broker.pulsar.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPomXml;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.docker.domain.DockerService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

@UnitTest
@ExtendWith(MockitoExtension.class)
class PulsarDomainServiceTest {

  @Mock
  BuildToolService buildToolService;

  @Mock
  ProjectRepository projectRepository;

  @Mock
  SpringBootCommonService springBootCommonService;

  @Mock
  DockerService dockerService;

  @InjectMocks
  PulsarDomainService pulsarDomainService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPomXml();
    when(dockerService.getImageNameWithVersion(anyString())).thenReturn(Optional.of("dummy"));

    pulsarDomainService.init(project);

    verify(buildToolService, times(1)).addVersionPropertyAndDependency(eq(project), eq("pulsar"), any(Dependency.class));
    verify(buildToolService, times(1)).addVersionPropertyAndDependency(eq(project), eq("testcontainers"), any(Dependency.class));
    verify(dockerService, times(1)).getImageNameWithVersion(anyString());
    verify(projectRepository, times(5)).template(any(ProjectFile.class));
    verify(springBootCommonService, times(1)).addProperties(eq(project), anyString(), any());
    verify(springBootCommonService, times(1)).addPropertiesComment(eq(project), anyString());
    verify(springBootCommonService, times(4)).addPropertiesTest(eq(project), anyString(), any());
    verify(springBootCommonService, times(1)).addPropertiesTestComment(eq(project), anyString());
    verify(springBootCommonService).updateIntegrationTestAnnotation(eq(project), anyString());
  }
}
