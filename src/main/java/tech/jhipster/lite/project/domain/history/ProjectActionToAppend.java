package tech.jhipster.lite.project.domain.history;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.project.domain.ProjectPath;

public record ProjectActionToAppend(ProjectPath path, ProjectAction action) {
  public ProjectActionToAppend {
    Assert.notNull("path", path);
    Assert.notNull("action", action);
  }
}
