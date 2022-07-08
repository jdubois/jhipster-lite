package tech.jhipster.lite.module.domain.packagejson;

import tech.jhipster.lite.error.domain.Assert;

public record ScriptCommand(String command) {
  public ScriptCommand {
    Assert.notBlank("command", command);
  }

  public String get() {
    return command();
  }
}
