package tech.jhipster.lite.generator.docker.domain;

import tech.jhipster.lite.error.domain.Assert;

public record DockerImage(String imageName, String version) {
  public DockerImage {
    Assert.notNull("imageName", imageName);
    Assert.notNull("version", version);
  }

  public String fullName() {
    return imageName() + ":" + version();
  }
}
