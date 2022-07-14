package tech.jhipster.lite.generator.buildtool.gradle.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.common.domain.WordUtils.*;
import static tech.jhipster.lite.generator.buildtool.gradle.domain.Gradle.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;

import java.util.Optional;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class GradleDomainService implements GradleService {

  public static final String SOURCE = "buildtool/gradle";

  private final ProjectRepository projectRepository;

  public GradleDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addDependency(Project project, Dependency dependency) {
    String newDependency = Gradle.getDependencyWithNeedle(dependency);
    projectRepository.replaceText(project, "", BUILD_GRADLE_KTS, REGEXP_SPACE_STAR + GRADLE_NEEDLE_DEPENDENCY, newDependency);
  }

  @Override
  public Optional<String> getGroup(String folder) {
    Assert.notBlank("folder", folder);

    return FileUtils.getValueBetween(getPath(folder, BUILD_GRADLE_KTS), "group = " + DQ, DQ);
  }
}
