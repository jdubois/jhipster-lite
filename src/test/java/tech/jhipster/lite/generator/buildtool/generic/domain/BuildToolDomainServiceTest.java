package tech.jhipster.lite.generator.buildtool.generic.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.gradle.domain.GradleService;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenService;
import tech.jhipster.lite.generator.project.domain.Project;

@UnitTest
@ExtendWith(MockitoExtension.class)
class BuildToolDomainServiceTest {

  @Mock
  private MavenService mavenService;

  @Mock
  private GradleService gradleService;

  @InjectMocks
  private BuildToolDomainService buildToolDomainService;

  @Nested
  class MavenTest {

    @Test
    void shouldAddDependency() {
      Project project = tmpProjectWithPomXml();
      Dependency dependency = getDependency();

      buildToolDomainService.addDependency(project, dependency);

      verify(mavenService).addDependency(project, dependency);
    }

    @Test
    void shouldAddDependencyWithExclusion() {
      Project project = tmpProjectWithPomXml();
      Dependency dependency = getDependency();
      List<Dependency> exclusions = getExclusions();

      buildToolDomainService.addDependency(project, dependency, exclusions);

      verify(mavenService).addDependency(project, dependency, exclusions);
    }

    @Test
    void shouldAddDependencyWithVersionProperty() {
      when(mavenService.getVersion("spring-boot")).thenReturn(Optional.of("0.0.0"));
      Project project = tmpProjectWithPomXml();
      Dependency dependency = getDependency();
      buildToolDomainService.addVersionPropertyAndDependency(project, "spring-boot", dependency);
      verify(mavenService).addDependency(eq(project), any(Dependency.class));
    }

    @Test
    void shouldAddDependencyManagement() {
      Project project = tmpProjectWithPomXml();
      Dependency dependency = getDependency();

      buildToolDomainService.addDependencyManagement(project, dependency);

      verify(mavenService).addDependencyManagement(project, dependency);
    }

    @Test
    void shouldAddProperty() {
      Project project = tmpProjectWithPomXml();

      buildToolDomainService.addProperty(project, "testcontainers.version", "0.0.0");

      verify(mavenService).addProperty(project, "testcontainers.version", "0.0.0");
    }

    @Test
    void shouldGetVersion() {
      Project project = tmpProjectWithPomXml();

      buildToolDomainService.getVersion(project, "spring-boot");

      verify(mavenService).getVersion("spring-boot");
    }

    @Test
    void shouldGetGroup() {
      Project project = tmpProjectWithPomXml();

      buildToolDomainService.getGroup(project);

      verify(mavenService).getGroupId(project.getFolder());
    }

    @Test
    void shouldGetName() {
      Project project = tmpProjectWithPomXml();

      buildToolDomainService.getName(project);

      verify(mavenService).getName(project.getFolder());
    }
  }

  @Nested
  class GradleTest {

    @Test
    void shouldGetGroup() {
      Project project = tmpProjectWithBuildGradle();

      buildToolDomainService.getGroup(project);

      verify(gradleService).getGroup(project.getFolder());
    }

    @Test
    void shouldAddDependency() {
      Project project = tmpProjectWithBuildGradle();
      Dependency dependency = getDependency();

      buildToolDomainService.addDependency(project, dependency);

      verify(gradleService).addDependency(project, dependency);
    }
  }

  @Nested
  class NoBuildToolTest {

    @Test
    void shouldNotAddDependency() {
      Project project = tmpProject();
      Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();

      assertThatThrownBy(() -> buildToolDomainService.addDependency(project, dependency)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotAddDependencyWithExclusion() {
      Project project = tmpProject();
      Dependency dependency = getDependency();
      List<Dependency> exclusions = getExclusions();

      assertThatThrownBy(() -> buildToolDomainService.addDependency(project, dependency, exclusions))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotAddDependencyManagement() {
      Project project = tmpProject();
      Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();

      assertThatThrownBy(() -> buildToolDomainService.addDependencyManagement(project, dependency))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotAddProperty() {
      Project project = tmpProject();

      assertThatThrownBy(() -> buildToolDomainService.addProperty(project, "testcontainers", "0.0.0"))
        .isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotGetVersion() {
      Project project = tmpProject();

      assertThatThrownBy(() -> buildToolDomainService.getVersion(project, "spring-boot")).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotGetGroup() {
      Project project = tmpProject();

      assertThatThrownBy(() -> buildToolDomainService.getGroup(project)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotGetName() {
      Project project = tmpProject();

      assertThatThrownBy(() -> buildToolDomainService.getName(project)).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldNotAddDependencyWithoutVersionProperty() {
      when(mavenService.getVersion("spring-boot")).thenReturn(Optional.empty());
      Project project = tmpProjectWithPomXml();
      Dependency dependency = getDependency();
      assertThatThrownBy(() -> buildToolDomainService.addVersionPropertyAndDependency(project, "spring-boot", dependency))
        .isExactlyInstanceOf(GeneratorException.class);
    }
  }

  private Dependency getDependency() {
    return Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-web").build();
  }

  private List<Dependency> getExclusions() {
    return List.of(Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-tomcat").build());
  }
}
