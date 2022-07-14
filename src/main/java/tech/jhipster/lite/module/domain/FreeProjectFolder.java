package tech.jhipster.lite.module.domain;

import java.nio.file.Paths;
import java.util.UUID;
import org.apache.commons.lang3.SystemUtils;

public class FreeProjectFolder implements JHipsterProjectFolderFactory {

  @Override
  public boolean isInvalid(String folderPath) {
    return false;
  }

  @Override
  public String generatePath() {
    return Paths.get(SystemUtils.JAVA_IO_TMPDIR).resolve(UUID.randomUUID().toString()).toString();
  }
}
