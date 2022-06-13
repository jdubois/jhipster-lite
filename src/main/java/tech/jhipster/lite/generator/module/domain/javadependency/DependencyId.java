package tech.jhipster.lite.generator.module.domain.javadependency;

import tech.jhipster.lite.error.domain.Assert;

public record DependencyId(GroupId groupId, ArtifactId artifactId) {
  public DependencyId {
    Assert.notNull("groupId", groupId);
    Assert.notNull("artifactId", artifactId);
  }
}
