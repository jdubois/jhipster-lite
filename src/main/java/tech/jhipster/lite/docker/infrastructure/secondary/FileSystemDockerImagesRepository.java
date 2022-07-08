package tech.jhipster.lite.docker.infrastructure.secondary;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.common.domain.ProjectFilesReader;
import tech.jhipster.lite.docker.domain.DockerImage;
import tech.jhipster.lite.docker.domain.DockerImageName;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.docker.domain.UnknownDockerImageException;
import tech.jhipster.lite.error.domain.Assert;

@Repository
class FileSystemDockerImagesRepository implements DockerImages {

  private static final String DOCKER_FROM = "from ";

  private final ProjectFilesReader files;

  public FileSystemDockerImagesRepository(ProjectFilesReader files) {
    this.files = files;
  }

  @Override
  public DockerImage get(DockerImageName imageName) {
    Assert.notNull("imageName", imageName);

    return Stream
      .of(files.readString("/generator/dependencies/Dockerfile").split("[\r\n]"))
      .map(String::trim)
      .map(String::toLowerCase)
      .filter(imageLine(imageName))
      .findFirst()
      .map(toDockerImage())
      .orElseThrow(() -> new UnknownDockerImageException(imageName));
  }

  private Predicate<String> imageLine(DockerImageName imageName) {
    return line -> line.startsWith(DOCKER_FROM + imageName.get() + ":");
  }

  private Function<String, DockerImage> toDockerImage() {
    return line -> {
      int versionSeparatorIndex = line.lastIndexOf(":");

      return new DockerImage(line.substring(DOCKER_FROM.length(), versionSeparatorIndex), line.substring(versionSeparatorIndex + 1));
    };
  }
}
