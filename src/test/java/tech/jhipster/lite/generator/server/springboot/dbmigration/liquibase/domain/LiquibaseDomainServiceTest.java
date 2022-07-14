package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.ProjectFilesAsserter.*;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.domain.LiquibaseDomainService.*;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.FilePath;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

@UnitTest
@ExtendWith(MockitoExtension.class)
class LiquibaseDomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @Mock
  BuildToolService buildToolService;

  @Mock
  SpringBootCommonService springBootCommonService;

  @Spy
  Clock clock = Clock.fixed(Instant.parse("2022-01-22T14:01:54.954396664Z"), ZoneId.of("Europe/Paris"));

  @InjectMocks
  LiquibaseDomainService liquibaseDomainService;

  @Test
  void shouldInit() {
    // Given
    Project project = tmpProject();

    when(buildToolService.getVersion(any(Project.class), anyString())).thenReturn(Optional.of("0.0.0"));

    // When
    liquibaseDomainService.init(project);

    // Then
    ArgumentCaptor<Dependency> dependencyArgCaptor = ArgumentCaptor.forClass(Dependency.class);
    verify(buildToolService, times(2)).addDependency(eq(project), dependencyArgCaptor.capture());
    List<Dependency> dependencies = dependencyArgCaptor.getAllValues();

    Dependency expectedDependency = Dependency
      .builder()
      .groupId("org.liquibase")
      .artifactId("liquibase-core")
      .version("\\${liquibase.version}")
      .build();
    assertThat(dependencies.get(0)).usingRecursiveComparison().isEqualTo(expectedDependency);

    expectedDependency = Dependency.builder().groupId("com.h2database").artifactId("h2").scope("test").build();
    assertThat(dependencies.get(1)).usingRecursiveComparison().isEqualTo(expectedDependency);

    verify(projectRepository)
      .add(
        projectFileArgument(
          project,
          new FilePath("server/springboot/dbmigration/liquibase/resources", LIQUIBASE_MASTER_XML),
          new FilePath("src/main/resources/" + CONFIG_LIQUIBASE, LIQUIBASE_MASTER_XML)
        )
      );

    ArgumentCaptor<ProjectFile> projectFileArgCaptor = ArgumentCaptor.forClass(ProjectFile.class);
    verify(projectRepository, times(6)).template(projectFileArgCaptor.capture());
    List<ProjectFile> projectFiles = projectFileArgCaptor.getAllValues();

    ProjectFile projectFile = ProjectFile
      .forProject(project)
      .withSource("server/springboot/dbmigration/liquibase/src", "AsyncSpringLiquibase.java")
      .withDestinationFolder(MAIN_JAVA + "/com/mycompany/myapp/" + LIQUIBASE_PATH);
    assertThat(projectFiles.get(0)).usingRecursiveComparison().isEqualTo(projectFile);
    projectFile =
      ProjectFile
        .forProject(project)
        .withSource("server/springboot/dbmigration/liquibase/src", "LiquibaseConfiguration.java")
        .withDestinationFolder(MAIN_JAVA + "/com/mycompany/myapp/" + LIQUIBASE_PATH);
    assertThat(projectFiles.get(1)).usingRecursiveComparison().isEqualTo(projectFile);
    projectFile =
      ProjectFile
        .forProject(project)
        .withSource("server/springboot/dbmigration/liquibase/src", "SpringLiquibaseUtil.java")
        .withDestinationFolder(MAIN_JAVA + "/com/mycompany/myapp/" + LIQUIBASE_PATH);
    assertThat(projectFiles.get(2)).usingRecursiveComparison().isEqualTo(projectFile);
    projectFile =
      ProjectFile
        .forProject(project)
        .withSource("server/springboot/dbmigration/liquibase/test", "AsyncSpringLiquibaseTest.java")
        .withDestinationFolder(TEST_JAVA + "/com/mycompany/myapp/" + LIQUIBASE_PATH);
    assertThat(projectFiles.get(3)).usingRecursiveComparison().isEqualTo(projectFile);
    projectFile =
      ProjectFile
        .forProject(project)
        .withSource("server/springboot/dbmigration/liquibase/test", "LiquibaseConfigurationIT.java")
        .withDestinationFolder(TEST_JAVA + "/com/mycompany/myapp/" + LIQUIBASE_PATH);
    assertThat(projectFiles.get(4)).usingRecursiveComparison().isEqualTo(projectFile);
    projectFile =
      ProjectFile
        .forProject(project)
        .withSource("server/springboot/dbmigration/liquibase/test", "SpringLiquibaseUtilTest.java")
        .withDestinationFolder(TEST_JAVA + "/com/mycompany/myapp/" + LIQUIBASE_PATH);
    assertThat(projectFiles.get(5)).usingRecursiveComparison().isEqualTo(projectFile);

    verify(springBootCommonService).addTestLogbackRecorder(any(Project.class));
    verify(springBootCommonService, times(3)).addLogger(any(Project.class), anyString(), any(Level.class));
    verify(springBootCommonService, times(3)).addLoggerTest(any(Project.class), anyString(), any(Level.class));
  }

  @Test
  void shouldNotAddLiquibaseDependency() {
    Project project = tmpProject();

    Assertions.assertThatThrownBy(() -> liquibaseDomainService.addLiquibase(project)).isExactlyInstanceOf(GeneratorException.class);
  }
}
