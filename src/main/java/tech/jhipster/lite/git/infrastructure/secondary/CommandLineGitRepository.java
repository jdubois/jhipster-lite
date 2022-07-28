package tech.jhipster.lite.git.infrastructure.secondary;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.git.domain.GitCommitMessage;
import tech.jhipster.lite.git.domain.GitRepository;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@Generated(reason = "Some error cases are too hard to test to be worth the effort")
class CommandLineGitRepository implements GitRepository {

  private static final Logger log = LoggerFactory.getLogger(CommandLineGitRepository.class);

  @Override
  public void init(JHipsterProjectFolder folder) {
    Assert.notNull("folder", folder);

    try {
      Process process = new ProcessBuilder("git", "init").directory(folderFile(folder)).start();

      if (failedExecution(process)) {
        throw new GitInitException("Error during git init, process failed");
      }

      traceProcess("git init", process);
    } catch (IOException e) {
      throw new GitInitException("Error during git init: " + e.getMessage(), e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();

      throw new GitInitException("Error during git init: " + e.getMessage(), e);
    }
  }

  @Override
  public void commitAll(JHipsterProjectFolder folder, GitCommitMessage message) {
    Assert.notNull("folder", folder);
    Assert.notNull("message", message);

    try {
      gitAdd(folder);

      gitCommit(folder, message);
    } catch (IOException e) {
      throw new GitCommitException("Error during git commit: " + e.getMessage(), e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();

      throw new GitCommitException("Error during git commit: " + e.getMessage(), e);
    }
  }

  private void gitAdd(JHipsterProjectFolder folder) throws IOException, InterruptedException {
    Process process = new ProcessBuilder("git", "add", "-A").directory(folderFile(folder)).start();

    if (failedExecution(process)) {
      throw new GitCommitException("Error during git commit, failed when adding files");
    }

    traceProcess("git add", process);
  }

  private void gitCommit(JHipsterProjectFolder folder, GitCommitMessage message) throws IOException, InterruptedException {
    Process process = new ProcessBuilder("git", "commit", "-m", message.get()).directory(folderFile(folder)).start();

    if (failedExecution(process)) {
      throw new GitCommitException("Error during git commit, failed when commiting files");
    }

    traceProcess("git commit", process);
  }

  private File folderFile(JHipsterProjectFolder folder) {
    return new File(folder.get());
  }

  private boolean failedExecution(Process process) throws InterruptedException {
    return !process.waitFor(10, TimeUnit.SECONDS);
  }

  private void traceProcess(String command, Process process) throws IOException {
    if (log.isTraceEnabled()) {
      log.trace("{}: {}", command, read(process.getInputStream()));
    }

    if (log.isErrorEnabled()) {
      String errors = read(process.getErrorStream());

      if (StringUtils.isNotBlank(errors)) {
        log.error("Error during {}: {}", command, errors);
      }
    }
  }

  private String read(InputStream stream) throws IOException {
    return new String(stream.readAllBytes(), StandardCharsets.UTF_8).intern();
  }
}
