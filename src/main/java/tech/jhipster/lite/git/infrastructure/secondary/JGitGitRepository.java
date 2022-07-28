package tech.jhipster.lite.git.infrastructure.secondary;

import java.io.File;
import java.io.IOException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.git.domain.GitCommitMessage;
import tech.jhipster.lite.git.domain.GitRepository;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

class JGitGitRepository implements GitRepository {

  @Override
  public void init(JHipsterProjectFolder folder) {
    Assert.notNull("folder", folder);

    try {
      Git.init().setDirectory(folderFile(folder)).call();
    } catch (IllegalStateException | GitAPIException | JGitInternalException e) {
      throw new GitInitException("Error during git init: " + e.getMessage(), e);
    }
  }

  @Override
  public void commitAll(JHipsterProjectFolder folder, GitCommitMessage message) {
    Assert.notNull("folder", folder);
    Assert.notNull("message", message);

    File folderFile = folderFile(folder);

    try (Git gitFolder = Git.open(folderFile);) {
      gitFolder.add().addFilepattern(".").call();

      gitFolder.commit().setMessage(message.get()).call();
    } catch (IOException | GitAPIException | JGitInternalException e) {
      throw new GitCommitException("Can't commit :" + e.getMessage(), e);
    }
  }

  private File folderFile(JHipsterProjectFolder folder) {
    return new File(folder.get());
  }
}
