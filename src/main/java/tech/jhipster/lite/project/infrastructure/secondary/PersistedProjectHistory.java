package tech.jhipster.lite.project.infrastructure.secondary;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;
import tech.jhipster.lite.project.domain.ProjectPath;
import tech.jhipster.lite.project.domain.history.ProjectHistory;

class PersistedProjectHistory {

  private final Collection<PersistedProjectAction> actions;

  private PersistedProjectHistory(@JsonProperty("actions") Collection<PersistedProjectAction> actions) {
    this.actions = actions;
  }

  static PersistedProjectHistory from(ProjectHistory history) {
    return new PersistedProjectHistory(history.actions().stream().map(PersistedProjectAction::from).toList());
  }

  public ProjectHistory toDomain(ProjectPath path) {
    return new ProjectHistory(path, getActions().stream().map(PersistedProjectAction::toDomain).toList());
  }

  public Collection<PersistedProjectAction> getActions() {
    return actions;
  }
}
