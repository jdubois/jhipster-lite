package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static tech.jhipster.lite.module.domain.JHipsterModule.comment;
import static tech.jhipster.lite.module.domain.JHipsterModule.propertyKey;
import static tech.jhipster.lite.module.domain.JHipsterModule.propertyValue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.module.domain.javaproperties.PropertyValue;

@UnitTest
class PropertiesFileSpringPropertiesBlockCommentsHandlerTest {

  @Test
  void shouldNotOrganizePropertiesWhenFileNotExists() {
    String path = TestFileUtils.tmpDirForTest();
    Path propetiesFile = Paths.get(path, "src/main/resources/config/application.properties");
    Map<PropertyKey, PropertyValue> properties = new LinkedHashMap<>();
    properties.put(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha"));
    properties.put(propertyKey("springdoc.swagger-ui.tagsSorter"), propertyValue("alpha"));
    properties.put(propertyKey("springdoc.swagger-ui.tryItOutEnabled"), propertyValue("alpha"));

    new PropertiesFileSpringPropertiesBlockCommentsHandler(propetiesFile).set(comment("Swagger properties"), properties);

    Throwable thrown = catchThrowable(() -> {
      content(Paths.get(path, "src/main/resources/config/application.properties"));
    });
    assertThat(thrown).hasCauseInstanceOf(NoSuchFileException.class);
  }

  private static String content(Path file) {
    try {
      return Files.readString(file);
    } catch (IOException e) {
      throw new AssertionError(e.getMessage(), e);
    }
  }
}
