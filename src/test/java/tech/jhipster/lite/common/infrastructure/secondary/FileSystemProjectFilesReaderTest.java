package tech.jhipster.lite.common.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.ProjectFilesReader;
import tech.jhipster.lite.error.domain.GeneratorException;

@UnitTest
class FileSystemProjectFilesReaderTest {

  private static final ProjectFilesReader files = new FileSystemProjectFilesReader();

  @Test
  void shouldNotReadUnknownFileContent() {
    assertThatThrownBy(() -> files.read("unknown")).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldReadFileContent() {
    assertThat(files.read("/generator/dependencies/Dockerfile")).isNotEmpty();
  }
}
