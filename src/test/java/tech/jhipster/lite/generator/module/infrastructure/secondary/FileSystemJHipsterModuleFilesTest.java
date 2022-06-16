package tech.jhipster.lite.generator.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.generator.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.generator.module.domain.JHipsterModule.from;

import ch.qos.logback.classic.Level;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.jhipster.lite.LogSpy;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.common.infrastructure.secondary.FileSystemProjectFilesReader;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.module.domain.JHipsterModule;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterProjectFolder;

@UnitTest
@ExtendWith(LogSpy.class)
class FileSystemJHipsterModuleFilesTest {

  private static final FileSystemJHipsterModuleFiles files = new FileSystemJHipsterModuleFiles(new FileSystemProjectFilesReader());

  private final LogSpy logs;

  public FileSystemJHipsterModuleFilesTest(LogSpy logs) {
    this.logs = logs;
  }

  @Test
  void shouldNotWriteOnUnwritablePath() {
    JHipsterProjectFolder project = new JHipsterProjectFolder(Paths.get("src/test/resources/generator").toAbsolutePath().toString());

    JHipsterModule module = moduleBuilder(JHipsterModuleProperties.defaultProperties(project))
      .files()
      .add(from("server/springboot/core/MainApp.java.mustache"), to("content"))
      .and()
      .build();

    assertThatThrownBy(() -> files.create(project, module.templatedFiles())).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldTraceAddedFiles() {
    JHipsterProjectFolder project = new JHipsterProjectFolder(FileUtils.tmpDirForTest());

    JHipsterModule module = moduleBuilder(JHipsterModuleProperties.defaultProperties(project))
      .files()
      .add(from("server/springboot/core/MainApp.java.mustache"), to("MainApp.java"))
      .and()
      .build();

    files.create(project, module.templatedFiles());

    logs.assertLogged(Level.DEBUG, "MainApp.java");
  }
}
