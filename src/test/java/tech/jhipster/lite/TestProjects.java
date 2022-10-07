package tech.jhipster.lite;

public final class TestProjects {

  private static String lastProjectFolder;

  private TestProjects() {}

  public static String newTestFolder() {
    return lastProjectFolder = TestFileUtils.tmpDirForTest();
  }

  public static String lastProjectFolder() {
    return lastProjectFolder;
  }
}
