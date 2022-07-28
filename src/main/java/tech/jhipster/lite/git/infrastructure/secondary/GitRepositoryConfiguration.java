package tech.jhipster.lite.git.infrastructure.secondary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.git.domain.GitRepository;

@Configuration
class GitRepositoryConfiguration {

  private static final Logger log = LoggerFactory.getLogger(GitRepositoryConfiguration.class);

  @Bean
  GitRepository gitRepository(GitChecker checker) {
    if (checker.hasGit()) {
      log.info("Using command line git repository");
      return new CommandLineGitRepository();
    }

    log.info("Command line not available, using JGit git repository");
    return new JGitGitRepository();
  }
}
