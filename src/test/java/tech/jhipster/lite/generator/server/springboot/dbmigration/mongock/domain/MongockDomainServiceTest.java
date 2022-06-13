package tech.jhipster.lite.generator.server.springboot.dbmigration.mongock.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.TestUtils.tmpProject;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

@UnitTest
@ExtendWith(MockitoExtension.class)
class MongockDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @Mock
  BuildToolService buildToolService;

  @Mock
  SpringBootCommonService springBootCommonService;

  @InjectMocks
  MongockDomainService mongockDomainService;

  @Test
  void shouldInit() {
    // Given
    Project project = tmpProject();

    when(buildToolService.getVersion(any(Project.class), anyString())).thenReturn(Optional.of("0.0.0"));

    // When
    mongockDomainService.init(project);

    // Then
    ArgumentCaptor<Dependency> dependencyArgCaptor = ArgumentCaptor.forClass(Dependency.class);
    verify(buildToolService).addDependencyManagement(eq(project), dependencyArgCaptor.capture());
    verify(buildToolService, times(2)).addDependency(eq(project), dependencyArgCaptor.capture());
    Dependency expectedDependency = Dependency
      .builder()
      .groupId("io.mongock")
      .artifactId("mongock-bom")
      .version("\\${mongock.version}")
      .scope("import")
      .type("pom")
      .build();
    List<Dependency> dependencyList = dependencyArgCaptor.getAllValues();
    assertThat(dependencyList).hasSize(3);
    assertThat(dependencyList.get(0)).usingRecursiveComparison().isEqualTo(expectedDependency);
    expectedDependency = Dependency.builder().groupId("io.mongock").artifactId("mongock-springboot").build();
    assertThat(dependencyList.get(1)).usingRecursiveComparison().isEqualTo(expectedDependency);
    expectedDependency = Dependency.builder().groupId("io.mongock").artifactId("mongodb-springdata-v3-driver").build();
    assertThat(dependencyList.get(2)).usingRecursiveComparison().isEqualTo(expectedDependency);

    verify(projectRepository, times(3)).template(any(ProjectFile.class));

    verify(springBootCommonService).addProperties(any(), anyString(), any());
    verify(springBootCommonService)
      .addProperties(
        project,
        "mongock.migration-scan-package",
        "com.mycompany.myapp.technical.infrastructure.secondary.mongock.dbmigration"
      );
    verify(springBootCommonService).addPropertiesTest(project, "mongock.enabled", false);
  }

  @Test
  void shouldNotAddMongockDependencyWhenVersionIsNotAvailable() {
    Project project = tmpProject();

    Assertions.assertThatThrownBy(() -> mongockDomainService.addMongockDependency(project)).isExactlyInstanceOf(GeneratorException.class);
  }
}
