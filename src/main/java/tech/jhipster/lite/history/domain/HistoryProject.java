package tech.jhipster.lite.history.domain;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

public record HistoryProject(JHipsterProjectFolder folder, String lineEnd) {
  private static final String BREAK = "\n";

  public HistoryProject(String folder, String lineEnd) {
    this(new JHipsterProjectFolder(folder), lineEnd);
  }

  public HistoryProject {
    Assert.notNull("folder", folder);
    Assert.notNull("lineEnd", lineEnd);
  }

  public static HistoryProject from(Project project) {
    Assert.notNull("project", project);

    return new HistoryProject(project.getFolder(), project.getEndOfLine());
  }

  public static HistoryProject from(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    return new HistoryProject(properties.projectFolder().get(), BREAK);
  }

  public Project toProject() {
    return Project.builder().folder(folder().get()).endOfLine(lineEnd()).build();
  }
}
