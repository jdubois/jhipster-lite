package tech.jhipster.lite.git.domain;

import tech.jhipster.lite.error.domain.Assert;

public record GitCommitMessage(String message) {
  public GitCommitMessage {
    Assert.notBlank("message", message);
  }

  public String get() {
    return message();
  }
}
