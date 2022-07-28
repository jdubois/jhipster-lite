package tech.jhipster.lite.git.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.GitTestUtil;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@UnitTest
class CommandLineGitRepositoryTest {

  private static final CommandLineGitRepository git = new CommandLineGitRepository();

  @Test
  void shouldInitProject() throws IOException {
    Path folderPath = gitInit();

    assertThat(Files.isDirectory(folderPath.resolve(".git"))).isTrue();
  }

  @Test
  void shouldCommitAllFiles() throws IOException {
    Path path = gitInit();
    Files.copy(Paths.get("src/test/resources/projects/files/application.properties"), path.resolve("application.properties"));

    git.commitAll(new JHipsterProjectFolder(path.toString()), "Add application.properties");

    assertThat(GitTestUtil.getCommits(path)).contains("Add application.properties");
  }

  private Path gitInit() throws IOException {
    String folder = TestFileUtils.tmpDirForTest();
    Path folderPath = Paths.get(folder);
    Files.createDirectories(folderPath);

    git.init(new JHipsterProjectFolder(folder));

    loadGitConfig(folderPath);

    return folderPath;
  }

  private void loadGitConfig(Path project) {
    GitTestUtil.execute(project, "config", "init.defaultBranch", "main");
    GitTestUtil.execute(project, "config", "user.email", "\"test@jhipster.com\"");
    GitTestUtil.execute(project, "config", "user.name", "\"Test\"");
  }
}
