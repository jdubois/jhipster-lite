package tech.jhipster.lite.module.infrastructure.secondary.docker;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.ProjectFilesReader;
import tech.jhipster.lite.module.domain.docker.DockerImageName;
import tech.jhipster.lite.module.domain.docker.DockerImageVersion;
import tech.jhipster.lite.module.domain.docker.DockerVersion;
import tech.jhipster.lite.module.domain.docker.UnknownDockerImageException;

@UnitTest
@ExtendWith(MockitoExtension.class)
class FileSystemDockerImagesReaderTest {

  @Test
  void shouldIgnoreInvalidVersionInFile() {
    FileSystemDockerImagesReader reader = mockedReader("FROM invalid\n" + " FROM mysql:8.0.29\n" + "FROM postgres:14.3");

    assertThatThrownBy(() -> reader.get().get(new DockerImageName("invalid"))).isExactlyInstanceOf(UnknownDockerImageException.class);
  }

  @Test
  void shouldGetVersionsFromFile() {
    FileSystemDockerImagesReader reader = mockedReader("FROM mongo:5.0.9\n" + " FROM mysql:8.0.29\n" + "FROM postgres:14.3\n");

    DockerImageVersion image = reader.get().get(new DockerImageName("mysql"));

    assertThat(image.name()).isEqualTo(new DockerImageName("mysql"));
    assertThat(image.version()).isEqualTo(new DockerVersion("8.0.29"));
    assertThat(image.fullName()).isEqualTo("mysql:8.0.29");
  }

  private FileSystemDockerImagesReader mockedReader(String content) {
    ProjectFilesReader files = mock(ProjectFilesReader.class);

    when(files.readString("/generator/dependencies/Dockerfile")).thenReturn(content);

    return new FileSystemDockerImagesReader(files);
  }
}
