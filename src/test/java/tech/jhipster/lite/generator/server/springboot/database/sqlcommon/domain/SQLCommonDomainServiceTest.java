package tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.*;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SQLCommonDomainServiceTest {

  @Mock
  BuildToolService buildToolService;

  @Mock
  SpringBootCommonService springBootCommonService;

  @Mock
  ProjectRepository projectRepository;

  @InjectMocks
  SQLCommonDomainService sqlCommonDomainService;

  @Test
  void shouldNotAddTestcontainersWithoutProject() {
    Map<String, Object> emptyMap = Map.of();
    assertThatThrownBy(() -> sqlCommonDomainService.addTestcontainers(null, "database", emptyMap))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldNotAddSpringDataJpaWithoutProject() {
    assertThatThrownBy(() -> sqlCommonDomainService.addSpringDataJpa(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldNotAddHikariWithoutProject() {
    assertThatThrownBy(() -> sqlCommonDomainService.addHikari(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldNotAddHibernateCoreTemplateWithoutProject() {
    assertThatThrownBy(() -> sqlCommonDomainService.addDockerComposeTemplate(null, "db"))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldNotAddHibernateCoreTemplateWithoutDatabase() {
    Project project = tmpProjectWithPomXml();
    assertThatThrownBy(() -> sqlCommonDomainService.addDockerComposeTemplate(project, null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("database");
  }

  @Test
  void shouldNotAddHibernateCoreTemplateWithEmptyDatabase() {
    Project project = tmpProjectWithPomXml();
    assertThatThrownBy(() -> sqlCommonDomainService.addDockerComposeTemplate(project, ""))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("database");
  }

  @Test
  void shouldNotAddJavaFilesWithoutProject() {
    assertThatThrownBy(() -> sqlCommonDomainService.addJavaFiles(null, "db"))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldNotAddPropertiesWithoutProject() {
    Map<String, Object> emptyMap = Map.of();
    assertThatThrownBy(() -> sqlCommonDomainService.addProperties(null, emptyMap))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldNotAddPropertiesWithoutProperties() {
    Project project = tmpProjectWithPomXml();
    assertThatThrownBy(() -> sqlCommonDomainService.addProperties(project, null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("properties");
  }

  @Test
  void shouldNotAddLoggersWithoutProject() {
    assertThatThrownBy(() -> sqlCommonDomainService.addLoggers(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldAddTestcontainers() {
    Project project = tmpProjectWithPomXml();

    sqlCommonDomainService.addTestcontainers(
      project,
      "postgresql",
      Map.of("spring.datasource.driver-class-name", "org.testcontainers.jdbc.ContainerDatabaseDriver")
    );
    verify(buildToolService).addVersionPropertyAndDependency(eq(project), eq("testcontainers"), any(Dependency.class));

    verify(springBootCommonService).addPropertiesTestComment(eq(project), anyString());
    verify(springBootCommonService).addPropertiesTest(eq(project), anyString(), anyString());
    verify(springBootCommonService).addPropertiesTestNewLine(project);
  }

  @Test
  void shouldAddSpringDataJpa() {
    Project project = tmpProjectWithPomXml();

    sqlCommonDomainService.addSpringDataJpa(project);
    verify(buildToolService).addDependency(eq(project), any(Dependency.class));
  }

  @Test
  void shouldAddHikari() {
    Project project = tmpProjectWithPomXml();

    sqlCommonDomainService.addHikari(project);
    verify(buildToolService).addDependency(eq(project), any(Dependency.class));
  }

  @Test
  void shouldAddHibernateCore() {
    Project project = tmpProjectWithPomXml();

    sqlCommonDomainService.addHibernateCore(project);
    verify(buildToolService).addDependency(eq(project), any(Dependency.class));
  }

  @Test
  void shouldAddDockerComposeTemplate() {
    Project project = tmpProjectWithPomXml();

    sqlCommonDomainService.addDockerComposeTemplate(project, "anyDB");
    verify(projectRepository).template(any(ProjectFile.class));
  }

  @Test
  void shouldAddJavaFiles() {
    Project project = tmpProjectWithPomXml();

    sqlCommonDomainService.addJavaFiles(project, "anyDB");
    verify(projectRepository).template(any(ProjectFile.class));
  }

  @Test
  void shouldAddProperties() {
    Project project = tmpProjectWithPomXml();
    Map<String, Object> extraProperties = Map.of("key", "value");

    sqlCommonDomainService.addProperties(project, extraProperties);

    verify(springBootCommonService).addPropertiesComment(project, "Database Configuration");
    verify(springBootCommonService, times(SQLCommon.springProperties().size() + extraProperties.size()))
      .addProperties(eq(project), any(String.class), any());
    verify(springBootCommonService).addPropertiesNewLine(project);
  }

  @Test
  void shouldAddLoggers() {
    Project project = tmpProjectWithPomXml();

    sqlCommonDomainService.addLoggers(project);

    final int loggersToAddNumber = SQLCommon.loggers().size();
    verify(springBootCommonService, times(loggersToAddNumber)).addLogger(eq(project), any(), any());
    verify(springBootCommonService, times(loggersToAddNumber)).addLoggerTest(eq(project), any(), any());
  }
}
