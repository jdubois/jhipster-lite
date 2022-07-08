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
  @Generated(reason = "The error handling is an hard to test implementation detail")
  public String readString(String path) {
    Assert.notBlank("path", path);

    try (InputStream input = getInputStream(path)) {
      assertFileExist(path, input);

      return toString(input);
    } catch (IOException e) {
      throw new GeneratorException("Error closing " + path + ": " + e.getMessage(), e);
    }
  }

  @Generated(reason = "The error handling is an hard to test implementation detail")
  private static String toString(InputStream input) {
    try {
      return IOUtils.toString(input, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new GeneratorException("Error reading file: " + e.getMessage(), e);
    }
  }

  @Override
  @Generated(reason = "The error handling is an hard to test implementation detail")
  public byte[] readBytes(String path) {
    Assert.notBlank("path", path);

    try (InputStream input = getInputStream(path)) {
      assertFileExist(path, input);

      return toByteArray(input);
    } catch (IOException e) {
      throw new GeneratorException("Error closing " + path + ": " + e.getMessage(), e);
    }
  }

  private void assertFileExist(String path, InputStream input) {
    if (input == null) {
      throw new GeneratorException("Can't find file: " + path);
    }
  }

  private InputStream getInputStream(String path) {
    return FileSystemProjectFilesReader.class.getResourceAsStream(path.replace("\\", SLASH));
  }

  @Generated(reason = "The error handling is an hard to test implementation detail")
  private static byte[] toByteArray(InputStream input) {
    try {
      return IOUtils.toByteArray(input);
    } catch (IOException e) {
      throw new GeneratorException("Error reading file: " + e.getMessage(), e);
    }
  }
}
