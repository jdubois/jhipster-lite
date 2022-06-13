package tech.jhipster.lite.generator.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.generator.module.domain.JHipsterModulesFixture.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.module.domain.javaproperties.SpringProperties;
import tech.jhipster.lite.generator.module.domain.javaproperties.SpringProperty;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterProjectFolder;

@UnitTest
class FileSystemSpringPropertiesCommandsHandlerTest {

  private static final FileSystemSpringPropertiesCommandsHandler handler = new FileSystemSpringPropertiesCommandsHandler();

  @Test
  void shouldCreateDefaultMainPropertiesForProjectWithoutProperties() {
    String folder = FileUtils.tmpDirForTest();

    handler.handle(new JHipsterProjectFolder(folder), properties(springMainProperty()));

    assertThat(content(Paths.get(folder, "src/main/resources/config/application.properties")))
      .contains("springdoc.swagger-ui.operationsSorter=alpha,beta");
  }

  @Test
  void shouldCreateProfileMainPropertiesForProjectWithoutProperties() {
    String folder = FileUtils.tmpDirForTest();

    handler.handle(new JHipsterProjectFolder(folder), properties(springLocalMainProperty()));

    assertThat(content(Paths.get(folder, "src/main/resources/config/application-local.properties")))
      .contains("springdoc.swagger-ui.operationsSorter=alpha,beta");
  }

  @Test
  void shouldCreateDefaultTestPropertiesForProjectWithoutProperties() {
    String folder = FileUtils.tmpDirForTest();

    handler.handle(new JHipsterProjectFolder(folder), properties(springTestProperty()));

    assertThat(content(Paths.get(folder, "src/test/resources/config/application.properties")))
      .contains("springdoc.swagger-ui.operationsSorter=alpha,beta");
  }

  @ParameterizedTest
  @ValueSource(strings = { "src/main/resources/config/application.properties", "src/main/resources/application.properties" })
  void shouldUpdateMainProperties(String propertiesPath) {
    String folder = FileUtils.tmpDirForTest();
    Path propetiesFile = Paths.get(folder, propertiesPath);
    loadDefaultProperties(propetiesFile);

    handler.handle(new JHipsterProjectFolder(folder), properties(springMainProperty()));

    assertThat(content(propetiesFile)).contains("springdoc.swagger-ui.operationsSorter=alpha,beta");
  }

  @ParameterizedTest
  @ValueSource(strings = { "src/test/resources/config/application.properties", "src/test/resources/application.properties" })
  void shouldUpdateTestProperties(String propertiesPath) {
    String folder = FileUtils.tmpDirForTest();
    Path propetiesFile = Paths.get(folder, propertiesPath);
    loadDefaultProperties(propetiesFile);

    handler.handle(new JHipsterProjectFolder(folder), properties(springTestProperty()));

    assertThat(content(propetiesFile)).contains("springdoc.swagger-ui.operationsSorter=alpha,beta");
  }

  private static SpringProperties properties(SpringProperty property) {
    return new SpringProperties(List.of(property));
  }

  private static String content(Path file) {
    try {
      return Files.readString(file);
    } catch (IOException e) {
      throw new AssertionError(e.getMessage(), e);
    }
  }

  private void loadDefaultProperties(Path propertiesFile) {
    try {
      Files.createDirectories(propertiesFile.getParent());

      Files.copy(Paths.get("src/test/resources/projects/project-with-spring-properties/application.properties"), propertiesFile);
    } catch (IOException e) {
      throw new AssertionError(e.getMessage(), e);
    }
  }
}
