package tech.jhipster.lite.generator.module.domain.javadependency;

import tech.jhipster.lite.error.domain.Assert;

public record GroupId(String groupId) {
  public GroupId {
    Assert.notBlank("groupId", groupId);
  }

  public String get() {
    return groupId();
  }
}
