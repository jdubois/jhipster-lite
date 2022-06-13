package tech.jhipster.lite.generator.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class FileSystemCurrentJavaDependenciesVersionsRepositoryTest {

  private static final FileSystemCurrentJavaDependenciesVersionsRepository versions = new FileSystemCurrentJavaDependenciesVersionsRepository();

  @Test
  void shouldGetCurrentVersions() {
    assertThat(versions.get()).isNotNull();
  }
}
