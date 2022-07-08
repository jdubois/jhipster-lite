package tech.jhipster.lite;

import java.util.UUID;
import tech.jhipster.lite.common.domain.FileUtils;

public final class TestFileUtils {

  private TestFileUtils() {}

  public static String tmpDirForTest() {
    return FileUtils.getPath(FileUtils.tmpDir(), "jhlite-test", UUID.randomUUID().toString());
  }
}
