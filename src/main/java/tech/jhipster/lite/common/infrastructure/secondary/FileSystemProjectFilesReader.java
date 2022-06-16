package tech.jhipster.lite.common.infrastructure.secondary;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.common.domain.ProjectFilesReader;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;

@Service
public class FileSystemProjectFilesReader implements ProjectFilesReader {

  private static final String SLASH = "/";

  @Override
  @Generated
  public String read(String path) {
    Assert.notBlank("path", path);

    try (InputStream input = FileSystemProjectFilesReader.class.getResourceAsStream(path.replace("\\", SLASH))) {
      if (input == null) {
        throw new GeneratorException("Can't find file: " + path);
      }

      return read(input);
    } catch (IOException e) {
      throw new GeneratorException("Error closing " + path + ": " + e.getMessage(), e);
    }
  }

  @Generated
  private static String read(InputStream input) {
    try {
      return IOUtils.toString(input, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new GeneratorException("Error reading file: " + e.getMessage(), e);
    }
  }
}
