package tech.jhipster.lite.generator.buildtool.gradle.domain;

import java.util.Optional;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;

public interface GradleService {
  void addDependency(Project project, Dependency dependency);

  Optional<String> getGroup(String folder);
}
