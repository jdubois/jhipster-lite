package tech.jhipster.lite.generator.buildtool.generic.domain;

import java.util.List;
import java.util.Optional;
import tech.jhipster.lite.generator.project.domain.Project;

public interface BuildToolService {
  void addDependency(Project project, Dependency dependency);
  void addDependency(Project project, Dependency dependency, List<Dependency> exclusions);
  void addDependencyManagement(Project project, Dependency dependency);
  void addProperty(Project project, String key, String value);
  void addVersionPropertyAndDependency(Project project, String versionProperty, Dependency dependency);

  Optional<String> getVersion(Project project, String name);

  Optional<String> getGroup(Project project);

  Optional<String> getName(Project project);
}
