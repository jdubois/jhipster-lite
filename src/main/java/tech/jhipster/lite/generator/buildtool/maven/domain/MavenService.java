package tech.jhipster.lite.generator.buildtool.maven.domain;

import java.util.List;
import java.util.Optional;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;

public interface MavenService {
  void addDependency(Project project, Dependency dependency);
  void addDependency(Project project, Dependency dependency, List<Dependency> exclusions);
  void addDependencyManagement(Project project, Dependency dependency);
  void addProperty(Project project, String key, String value);
  void deleteProperty(Project project, String key);

  Optional<String> getVersion(String name);

  Optional<String> getGroupId(String folder);

  Optional<String> getName(String folder);
}
