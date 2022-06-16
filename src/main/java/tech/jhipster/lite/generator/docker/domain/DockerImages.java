package tech.jhipster.lite.generator.docker.domain;

public interface DockerImages {
  /**
   * Get docker image information
   *
   * @param imageName
   *          name of the image to get information for
   * @return The docker image with this name
   * @throws UnknownDockerImageException
   *           if the image can't be found
   */
  DockerImage get(DockerImageName imageName);

  /**
   * Get docker image information
   *
   * @param imageName
   *          name of the image to get information for
   * @return The docker image with this name
   * @throws UnknownDockerImageException
   *           if the image can't be found
   */
  default DockerImage get(String imageName) {
    return get(new DockerImageName(imageName));
  }
}
