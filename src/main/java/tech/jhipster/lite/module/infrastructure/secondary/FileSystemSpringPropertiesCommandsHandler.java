package tech.jhipster.lite.module.infrastructure.secondary;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.javaproperties.SpringProperties;
import tech.jhipster.lite.module.domain.javaproperties.SpringProperty;
import tech.jhipster.lite.module.domain.javaproperties.SpringPropertyType;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@Service
class FileSystemSpringPropertiesCommandsHandler {

  private static final String DEFAULT_MAIN_FOLDER = "src/main/resources/config/";
  private static final String DEFAULT_TEST_FOLDER = "src/test/resources/config/";
  private static final Map<SpringPropertyType, List<String>> PROPERTIES_PATHS = buildPaths();

  private static Map<SpringPropertyType, List<String>> buildPaths() {
    return Map.of(
      SpringPropertyType.MAIN_PROPERTIES,
      List.of(DEFAULT_MAIN_FOLDER, "src/main/resources/"),
      SpringPropertyType.MAIN_BOOTSTRAP_PROPERTIES,
      List.of(DEFAULT_MAIN_FOLDER, "src/main/resources/"),
      SpringPropertyType.TEST_PROPERTIES,
      List.of(DEFAULT_TEST_FOLDER, "src/test/resources/"),
      SpringPropertyType.TEST_BOOTSTRAP_PROPERTIES,
      List.of(DEFAULT_TEST_FOLDER, "src/test/resources/")
    );
  }

  public void handle(JHipsterProjectFolder projectFolder, SpringProperties properties) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("properties", properties);

    properties.get().forEach(setProperty(projectFolder));
  }

  private Consumer<SpringProperty> setProperty(JHipsterProjectFolder projectFolder) {
    return property -> new PropertiesFileSpringPropertiesHandler(getPath(projectFolder, property)).set(property.key(), property.value());
  }

  private static Path getPath(JHipsterProjectFolder projectFolder, SpringProperty property) {
    return PROPERTIES_PATHS
      .get(property.type())
      .stream()
      .map(toFilePath(projectFolder, property))
      .filter(Files::exists)
      .findFirst()
      .orElseGet(defaultPropertiesFile(projectFolder, property));
  }

  private static Function<String, Path> toFilePath(JHipsterProjectFolder projectFolder, SpringProperty property) {
    return folder -> projectFolder.filePath(folder + propertiesFilename(property));
  }

  @Generated(reason = "Jacoco thinks there is a missed branch")
  private static Supplier<Path> defaultPropertiesFile(JHipsterProjectFolder projectFolder, SpringProperty property) {
    return switch (property.type()) {
      case MAIN_PROPERTIES -> () -> projectFolder.filePath(DEFAULT_MAIN_FOLDER + propertiesFilename(property));
      case MAIN_BOOTSTRAP_PROPERTIES -> () -> projectFolder.filePath(DEFAULT_MAIN_FOLDER + propertiesFilename(property));
      case TEST_PROPERTIES -> () -> projectFolder.filePath(DEFAULT_TEST_FOLDER + propertiesFilename(property));
      case TEST_BOOTSTRAP_PROPERTIES -> () -> projectFolder.filePath(DEFAULT_TEST_FOLDER + propertiesFilename(property));
    };
  }

  private static String propertiesFilename(SpringProperty property) {
    return property.filename() + ".properties";
  }
}
