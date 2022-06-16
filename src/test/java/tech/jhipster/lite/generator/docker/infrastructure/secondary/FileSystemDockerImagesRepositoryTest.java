package tech.jhipster.lite.generator.docker.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.ProjectFilesReader;
import tech.jhipster.lite.generator.docker.domain.DockerImage;
import tech.jhipster.lite.generator.docker.domain.DockerImageName;
import tech.jhipster.lite.generator.docker.domain.UnknownDockerImageException;

@UnitTest
@ExtendWith(MockitoExtension.class)
class FileSystemDockerImagesRepositoryTest {

  @Mock
  private ProjectFilesReader files;

  @InjectMocks
  private FileSystemDockerImagesRepository dockerImages;

  @Test
  void shouldNotGetImageFromEmptyFile() {
    mockDockerFile("");

    assertThatThrownBy(() -> dockerImages.get(new DockerImageName("mysql"))).isExactlyInstanceOf(UnknownDockerImageException.class);
  }

  @Test
  void shouldNotGetImageFromUnkownImage() {
    mockDockerFile("FROM mysql:8.0.29");

    assertThatThrownBy(() -> dockerImages.get(new DockerImageName("unknown"))).isExactlyInstanceOf(UnknownDockerImageException.class);
  }

  @Test
  void shouldGetImageFromFile() {
    mockDockerFile("FROM mongo:5.0.9\n" + " FROM mysql:8.0.29\n" + "FROM postgres:14.3");

    DockerImage dockerImage = dockerImages.get(new DockerImageName("MYsql"));

    assertThat(dockerImage.imageName()).isEqualTo("mysql");
    assertThat(dockerImage.version()).isEqualTo("8.0.29");
    assertThat(dockerImage.fullName()).isEqualTo("mysql:8.0.29");
  }

  private OngoingStubbing<String> mockDockerFile(String content) {
    return when(files.read("/generator/dependencies/Dockerfile")).thenReturn(content);
  }
}
