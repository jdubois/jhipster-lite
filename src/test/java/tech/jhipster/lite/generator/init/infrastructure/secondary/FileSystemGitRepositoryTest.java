package tech.jhipster.lite.generator.init.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@UnitTest
class FileSystemGitRepositoryTest {

  private static final FileSystemGitRepository git = new FileSystemGitRepository();

  @Test
  @EnabledOnOs(OS.LINUX)
  void shouldHandleGitInitException() throws IOException {
    String folder = createReadOnlyFolder();

    assertThatThrownBy(() -> git.init(new JHipsterProjectFolder(folder))).isExactlyInstanceOf(GitInitException.class);
  }

  private String createReadOnlyFolder() throws IOException {
    String folder = TestFileUtils.tmpDirForTest();

    Path folderPath = Paths.get(folder);
    Files.createDirectories(folderPath);
    Files.setPosixFilePermissions(folderPath, Set.of(PosixFilePermission.OWNER_READ));

    return folder;
  }

  @Test
  void shouldInitProject() throws IOException {
    String folder = TestFileUtils.tmpDirForTest();
    Path folderPath = Paths.get(folder);
    Files.createDirectories(folderPath);

    git.init(new JHipsterProjectFolder(folder));

    assertThat(Files.exists(folderPath.resolve(".git"))).isTrue();
  }
}
