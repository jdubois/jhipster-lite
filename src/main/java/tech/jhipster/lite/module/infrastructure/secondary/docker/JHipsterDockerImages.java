package tech.jhipster.lite.module.infrastructure.secondary.docker;

import java.util.Collection;
import java.util.function.Supplier;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.common.domain.Memoizers;
import tech.jhipster.lite.module.domain.docker.DockerImageVersions;
import tech.jhipster.lite.module.domain.docker.DockerImages;

@Repository
class JHipsterDockerImages implements DockerImages {

  private final Supplier<DockerImageVersions> versions;

  public JHipsterDockerImages(Collection<DockerImagesReader> readers) {
    versions = Memoizers.of(versionsReader(readers));
  }

  private Supplier<DockerImageVersions> versionsReader(Collection<DockerImagesReader> readers) {
    return () -> readers.stream().map(DockerImagesReader::get).reduce(DockerImageVersions.EMPTY, DockerImageVersions::merge);
  }

  @Override
  public DockerImageVersions get() {
    return versions.get();
  }
}
