package tech.jhipster.lite.history.domain;

import tech.jhipster.lite.TestFileUtils;

public final class HistoryProjectsFixture {

  private HistoryProjectsFixture() {}

  public static HistoryProject tmpProject() {
    return new HistoryProject(TestFileUtils.tmpDirForTest(), "\n");
  }
}
