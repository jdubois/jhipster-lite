package tech.jhipster.lite.module.domain.docker;

import tech.jhipster.lite.error.domain.GeneratorException;

public class UnknownDockerImageException extends GeneratorException {

  public UnknownDockerImageException(DockerImageName imageName) {
    super("Can't find image " + imageName.get() + ", forgot to add it to src/main/resources/generator/dependencies/Dockerfile?");
  }
}
