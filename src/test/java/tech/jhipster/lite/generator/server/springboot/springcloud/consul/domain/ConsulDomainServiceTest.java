package tech.jhipster.lite.generator.server.springboot.springcloud.consul.domain;

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
import tech.jhipster.lite.generator.docker.domain.DockerImage;
import tech.jhipster.lite.generator.docker.domain.DockerImages;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;

@UnitTest
@ExtendWith(MockitoExtension.class)
class ConsulDomainServiceTest {

  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private BuildToolService buildToolService;

  @Mock
  private SpringCloudCommonService springCloudCommonService;

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private ConsulDomainService consulDomainService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPomXml();
    when(buildToolService.getVersion(project, "spring-cloud")).thenReturn(Optional.of("0.0.0"));
    when(dockerImages.get("consul")).thenReturn(new DockerImage("consul", "1.1.1"));
    when(dockerImages.get("jhipster/consul-config-loader")).thenReturn(new DockerImage("jhipster/consul-config-loader", "2.2.2"));

    consulDomainService.init(project);

    verify(springCloudCommonService).addSpringCloudCommonDependencies(project);
    verify(buildToolService, times(2)).addDependency(any(Project.class), any(Dependency.class));

    verify(springCloudCommonService, times(3)).addOrMergeBootstrapProperties(any(Project.class), anyString(), anyString(), anyString());
    verify(projectRepository, times(2)).template(any(ProjectFile.class));
  }

  @Test
  void shouldNotAddDependencies() {
    Project project = tmpProjectWithPomXml();

    assertThatThrownBy(() -> consulDomainService.addDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }
}
