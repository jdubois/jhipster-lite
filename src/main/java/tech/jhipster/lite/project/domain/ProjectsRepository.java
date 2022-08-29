package tech.jhipster.lite.project.domain;

import java.util.Optional;
import tech.jhipster.lite.project.domain.download.Project;
import tech.jhipster.lite.project.domain.history.ProjectHistory;

public interface ProjectsRepository {
  void format(ProjectPath path);

  Optional<Project> get(ProjectPath path);

  void save(ProjectHistory history);

  ProjectHistory getHistory(ProjectPath path);
}
