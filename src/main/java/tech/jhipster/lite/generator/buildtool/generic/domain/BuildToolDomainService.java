package tech.jhipster.lite.generator.buildtool.generic.domain;

import java.util.List;
import java.util.Optional;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.gradle.domain.GradleService;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenService;
import tech.jhipster.lite.generator.project.domain.Project;

public class BuildToolDomainService implements BuildToolService {

  public static final String EXCEPTION_NO_BUILD_TOOL = "No build tool";
  private final MavenService mavenService;
  private final GradleService gradleService;

  public BuildToolDomainService(MavenService mavenService, GradleService gradleService) {
    this.mavenService = mavenService;
    this.gradleService = gradleService;
  }

  @Override
  public void addDependency(Project project, Dependency dependency) {
    if (project.isMavenProject()) {
      mavenService.addDependency(project, dependency);
    } else if (project.isGradleProject()) {
      gradleService.addDependency(project, dependency);
    } else {
      throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
    }
  }

  @Override
  public void addVersionPropertyAndDependency(Project project, String versionProperty, Dependency dependency) {
    String version = getVersion(project, versionProperty).orElseThrow(() -> new GeneratorException(versionProperty + " version not found"));

    Dependency dependencyWithVersion = dependency.toBuilder().version("\\${" + versionProperty + ".version}").build();
    addProperty(project, versionProperty + ".version", version);
    addDependency(project, dependencyWithVersion);
  }

  @Override
  public void addDependency(Project project, Dependency dependency, List<Dependency> exclusions) {
    if (project.isMavenProject()) {
      mavenService.addDependency(project, dependency, exclusions);
    } else {
      throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
    }
  }

  @Override
  public void addDependencyManagement(Project project, Dependency dependency) {
    if (project.isMavenProject()) {
      mavenService.addDependencyManagement(project, dependency);
    } else {
      throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
    }
  }

  @Override
  public void addProperty(Project project, String key, String value) {
    if (project.isMavenProject()) {
      mavenService.addProperty(project, key, value);
    } else {
      throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
    }
  }

  @Override
  public Optional<String> getVersion(Project project, String name) {
    if (project.isMavenProject()) {
      return mavenService.getVersion(name);
    }
    throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
  }

  @Override
  public Optional<String> getGroup(Project project) {
    if (project.isMavenProject()) {
      return mavenService.getGroupId(project.getFolder());
    } else if (project.isGradleProject()) {
      return gradleService.getGroup(project.getFolder());
    }
    throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
  }

  @Override
  public Optional<String> getName(Project project) {
    if (project.isMavenProject()) {
      return mavenService.getName(project.getFolder());
    }
    throw new GeneratorException(EXCEPTION_NO_BUILD_TOOL);
  }
}
