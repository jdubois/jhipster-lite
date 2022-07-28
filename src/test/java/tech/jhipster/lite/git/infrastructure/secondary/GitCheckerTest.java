package tech.jhipster.lite.git.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class GitCheckerTest {

  private static final GitChecker checker = new GitChecker();

  @Test
  void shouldHaveGitAvailable() {
    assertThat(checker.hasGit()).isTrue();
  }
}
