package tech.jhipster.lite.generator.module.domain.javadependency;

import tech.jhipster.lite.error.domain.Assert;

public record ArtifactId(String artifactId) {
  public ArtifactId {
    Assert.notBlank("artifactId", artifactId);
  }

  public String get() {
    return artifactId();
  }
}
