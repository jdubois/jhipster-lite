package tech.jhipster.lite.generator.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.generator.module.domain.JHipsterModule.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.FileUtils;

@UnitTest
class PropertiesFileSpringPropertiesHandlerTest {

  @Test
  void shouldCreateUnknownFile() {
    Path propertiesFile = Paths.get(FileUtils.tmpDirForTest(), "src/main/resources/application.properties");
    PropertiesFileSpringPropertiesHandler handler = new PropertiesFileSpringPropertiesHandler(propertiesFile);

    handler.set(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha", "beta"));

    assertThat(content(propertiesFile)).contains("springdoc.swagger-ui.operationsSorter=alpha,beta");
  }

  @Test
  void shouldAppendPropertyToFileWithProperties() {
    Path propertiesFile = Paths.get(FileUtils.tmpDirForTest(), "src/main/resources/application.properties");
    loadDefaultProperties(propertiesFile);
    PropertiesFileSpringPropertiesHandler handler = new PropertiesFileSpringPropertiesHandler(propertiesFile);

    handler.set(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha", "beta"));

    assertThat(content(propertiesFile))
      .contains("spring.application.name=JHLite")
      .endsWith("\nspringdoc.swagger-ui.operationsSorter=alpha,beta");
  }

  @Test
  void shouldReplaceExistingProperty() {
    Path propertiesFile = Paths.get(FileUtils.tmpDirForTest(), "src/main/resources/application.properties");
    loadDefaultProperties(propertiesFile);
    PropertiesFileSpringPropertiesHandler handler = new PropertiesFileSpringPropertiesHandler(propertiesFile);

    handler.set(propertyKey("spring.application.name"), propertyValue("alpha"));

    assertThat(content(propertiesFile)).startsWith("spring.application.name=alpha").doesNotContain("spring.application.name=JHLite");
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
