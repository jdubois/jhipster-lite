package tech.jhipster.lite.git.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;

@UnitTest
@ExtendWith(MockitoExtension.class)
class GitRepositoryConfigurationTest {

  private static final GitRepositoryConfiguration configuration = new GitRepositoryConfiguration();

  @Mock
  private GitChecker checker;

  @Test
  void shouldCreateJGitRepositoryWithGitNotAvailable() {
    assertThat(configuration.gitRepository(checker)).isExactlyInstanceOf(JGitGitRepository.class);
  }

  @Test
  void shouldCreateCommandLineRepositoryWithGitAvailable() {
    when(checker.hasGit()).thenReturn(true);

    assertThat(configuration.gitRepository(checker)).isExactlyInstanceOf(CommandLineGitRepository.class);
  }
}
