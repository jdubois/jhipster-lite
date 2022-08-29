package tech.jhipster.lite.module.domain.file;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.projectfile.domain.ProjectFilesReader;
import tech.jhipster.lite.projectfile.infrastructure.secondary.FileSystemProjectFilesReader;

@UnitTest
class JHipsterFileContentTest {

  private static final ProjectFilesReader files = new FileSystemProjectFilesReader();

  @Test
  void shouldNotReadUnknownFile() {
    JHipsterFileContent content = content("dummy");

    assertThatThrownBy(() -> content.read(files, context())).isExactlyInstanceOf(GeneratorException.class).hasMessageContaining("dummy");
  }

  @Test
  void shouldReadNotTemplatedContent() {
    JHipsterFileContent content = content("/generator/content/no-template.txt");

    assertThat(content.read(files, context())).isEqualTo("This is my content".getBytes(StandardCharsets.UTF_8));
  }

  @Test
  void shouldReadTemplatedContent() {
    JHipsterFileContent content = content("/generator/content/template.txt.mustache");

    assertThat(content.read(files, context())).isEqualTo("This is com.test.myapp".getBytes(StandardCharsets.UTF_8));
  }

  @Test
  void shouldGetRawContentForNotTemplatedFile() throws IOException {
    JHipsterFileContent content = content("/generator/client/vue/webapp/content/images/JHipster-Lite-neon-green.png");

    assertThat(content.read(files, context()))
      .isEqualTo(
        Files.readAllBytes(Paths.get("src/main/resources/generator/client/vue/webapp/content/images/JHipster-Lite-neon-green.png"))
      );
  }

  private static JHipsterFileContent content(String path) {
    return new JHipsterFileContent(new JHipsterSource(Paths.get(path)));
  }
}
