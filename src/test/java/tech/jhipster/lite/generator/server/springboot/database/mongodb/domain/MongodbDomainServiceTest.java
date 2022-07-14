package tech.jhipster.lite.generator.server.springboot.database.mongodb.domain;

import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.*;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.docker.domain.DockerImage;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain.SQLCommonService;

@UnitTest
@ExtendWith(MockitoExtension.class)
class MongodbDomainServiceTest {

  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private BuildToolService buildToolService;

  @Mock
  private SpringBootCommonService springBootCommonService;

  @Mock
  private SQLCommonService sqlCommonService;

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private MongodbDomainService mongodbDomainService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPomXml();

    when(dockerImages.get("mongo")).thenReturn(new DockerImage("mongo", "0.0.0"));

    mongodbDomainService.init(project);

    verify(buildToolService, times(2)).addDependency(any(Project.class), any(Dependency.class));

    verify(projectRepository, times(6)).template(any(ProjectFile.class));

    verify(springBootCommonService).addPropertiesComment(any(Project.class), anyString());
    verify(springBootCommonService, times(2)).addProperties(any(Project.class), anyString(), any());
    verify(springBootCommonService).addPropertiesNewLine(any(Project.class));
    verify(springBootCommonService, times(2)).addLogger(any(Project.class), anyString(), any(Level.class));
    verify(springBootCommonService, times(4)).addLoggerTest(any(Project.class), anyString(), any(Level.class));
    verify(springBootCommonService).updateIntegrationTestAnnotation(any(Project.class), anyString());

    verify(sqlCommonService).addDockerComposeTemplate(project, "mongodb");
    verify(sqlCommonService).addTestcontainers(project, "mongodb", Map.of());
  }
}
