package tech.jhipster.lite.generator.module.domain;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;

public final class FilesReader {

  private static final String SLASH = "/";

  private FilesReader() {}

  @Generated
  public static String readContent(String path) {
    Assert.notBlank("path", path);

    try (InputStream input = FilesReader.class.getResourceAsStream(path.replace("\\", SLASH))) {
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
