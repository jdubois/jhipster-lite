package tech.jhipster.lite.project.domain.history;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import tech.jhipster.lite.project.domain.ProjectPath;

public final class ProjectHistoryFixture {

  private ProjectHistoryFixture() {}

  public static ProjectHistory projectHistory() {
    return new ProjectHistory(projectPath(), List.of(projectAction()));
  }

  public static ProjectPath projectPath() {
    return new ProjectPath("/tmp/test-project");
  }

  public static ProjectAction projectAction() {
    return ProjectAction.builder().module("test-module").date(Instant.parse("2021-12-03T10:15:30.00Z")).properties(Map.of("key", "value"));
  }
}
