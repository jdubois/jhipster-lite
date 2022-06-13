package tech.jhipster.lite.generator.history.domain;

import tech.jhipster.lite.common.domain.FileUtils;

public final class HistoryProjectsFixture {

  private HistoryProjectsFixture() {}

  public static HistoryProject tmpProject() {
    return new HistoryProject(FileUtils.tmpDirForTest(), "\n");
  }
}
