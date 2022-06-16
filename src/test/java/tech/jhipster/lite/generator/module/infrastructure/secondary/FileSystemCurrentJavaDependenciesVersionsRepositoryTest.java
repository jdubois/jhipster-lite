package tech.jhipster.lite.generator.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.infrastructure.secondary.FileSystemProjectFilesReader;

@UnitTest
class FileSystemCurrentJavaDependenciesVersionsRepositoryTest {

  private static final FileSystemCurrentJavaDependenciesVersionsRepository versions = new FileSystemCurrentJavaDependenciesVersionsRepository(
    new FileSystemProjectFilesReader()
  );

  @Test
  void shouldGetCurrentVersions() {
    assertThat(versions.get()).isNotNull();
  }
}
