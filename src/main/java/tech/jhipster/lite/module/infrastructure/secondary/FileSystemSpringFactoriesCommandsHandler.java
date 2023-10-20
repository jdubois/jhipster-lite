package tech.jhipster.lite.module.infrastructure.secondary;

import static tech.jhipster.lite.module.infrastructure.secondary.FileSystemJHipsterModulesRepository.TEST_META_INF_FOLDER;

import java.nio.file.Path;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.module.domain.javaproperties.SpringFactories;
import tech.jhipster.lite.module.domain.javaproperties.SpringFactory;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.shared.error.domain.Assert;

@Service
class FileSystemSpringFactoriesCommandsHandler {

  public void handle(JHipsterProjectFolder projectFolder, SpringFactories factories) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("factories", factories);

    factories.get().forEach(setProperty(projectFolder));
  }

  private Consumer<SpringFactory> setProperty(JHipsterProjectFolder projectFolder) {
    return property -> new PropertiesFileSpringFactoriesHandler(getPath(projectFolder, property)).append(property.key(), property.value());
  }

  private static Path getPath(JHipsterProjectFolder projectFolder, SpringFactory factory) {
    return switch (factory.type()) {
      case TEST_FACTORIES -> projectFolder.filePath(TEST_META_INF_FOLDER + "spring.factories");
    };
  }
}
