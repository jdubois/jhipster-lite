package tech.jhipster.lite;

import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.UUID;

public final class TestFileUtils {

  private TestFileUtils() {}

  public static String tmpDirForTest() {
    return Paths.get(tmpDir()).resolve("jhlite-test").resolve(UUID.randomUUID().toString()).toString().replace("\\", "/");
  }

  private static String tmpDir() {
    String tempDir = System.getProperty("java.io.tmpdir");
    String fileSeparator = FileSystems.getDefault().getSeparator();

    if (tempDir.endsWith(fileSeparator)) {
      return tempDir.substring(0, tempDir.length() - fileSeparator.length());
    }

    return tempDir;
  }
}
