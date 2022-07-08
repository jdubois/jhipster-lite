package tech.jhipster.lite.docker.domain;

import tech.jhipster.lite.error.domain.Assert;

public record DockerImageName(String imageName) {
  public DockerImageName(String imageName) {
    Assert.notBlank("imageName", imageName);

    this.imageName = imageName.toLowerCase();
  }

  public String get() {
    return imageName();
  }
}
