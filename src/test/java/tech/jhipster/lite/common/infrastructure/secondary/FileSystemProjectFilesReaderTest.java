package tech.jhipster.lite.common.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.ProjectFilesReader;
import tech.jhipster.lite.error.domain.GeneratorException;

@UnitTest
class FileSystemProjectFilesReaderTest {

  private static final ProjectFilesReader files = new FileSystemProjectFilesReader();

  @Nested
  @DisplayName("String")
  class FileSystemProjectFilesReaderStringTest {

    @Test
    void shouldNotReadUnknownFileContent() {
      assertThatThrownBy(() -> files.readString("unknown")).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldReadFileContent() {
      assertThat(files.readString("/generator/dependencies/Dockerfile")).isNotEmpty();
    }
  }

  @Nested
  @DisplayName("Bytes")
  class FileSystemProjectFilesReaderBytesTest {

    @Test
    void shouldNotReadUnknownFileContent() {
      assertThatThrownBy(() -> files.readBytes("unknown")).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldReadFileContent() {
      assertThat(files.readBytes("/generator/dependencies/Dockerfile")).isNotEmpty();
    }
  }
}
