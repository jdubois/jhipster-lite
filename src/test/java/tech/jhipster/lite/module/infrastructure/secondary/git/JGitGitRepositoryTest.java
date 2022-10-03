package tech.jhipster.lite.module.infrastructure.secondary.git;

import static org.assertj.core.api.Assertions.*;

import ch.qos.logback.classic.Level;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.jhipster.lite.LogsSpy;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@UnitTest
@ExtendWith(LogsSpy.class)
class JGitGitRepositoryTest {

  private static final JGitGitRepository git = new JGitGitRepository();

  private final LogsSpy logs;

  public JGitGitRepositoryTest(LogsSpy logs) {
    this.logs = logs;
  }

  @Nested
  @DisplayName("init")
  class JGitGitRepositoryInitTest {

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
      Path path = gitInit();

      assertThat(Files.isDirectory(path.resolve(".git"))).isTrue();
    }

    @Test
    void shouldNotInitAlreadyInitializedProject() throws IOException {
      Path path = gitInit();

      git.init(new JHipsterProjectFolder(path.toString()));

      logs.shouldHave(Level.DEBUG, "already");
    }
  }

  @Nested
  @DisplayName("Commit all")
  class JGitGitRepositoryCommitTest {

    @Test
    void shouldHandleCommitErrors() {
      assertThatThrownBy(() -> git.commitAll(new JHipsterProjectFolder(TestFileUtils.tmpDirForTest()), "Add application.properties"))
        .isExactlyInstanceOf(GitCommitException.class);
    }

    @Test
    void shouldCommitAllFiles() throws IOException {
      Path path = gitInit();
      Files.copy(Paths.get("src/test/resources/projects/files/application.properties"), path.resolve("application.properties"));

      git.commitAll(new JHipsterProjectFolder(path.toString()), "Add application.properties");

      assertThat(GitTestUtil.getCommits(path)).contains("Add application.properties");
      assertThat(GitTestUtil.getCurrentBranch(path)).contains("main");
    }
  }

  private Path gitInit() throws IOException {
    String folder = TestFileUtils.tmpDirForTest();
    Path folderPath = Paths.get(folder);
    Files.createDirectories(folderPath);

    git.init(new JHipsterProjectFolder(folder));

    return folderPath;
  }
}
