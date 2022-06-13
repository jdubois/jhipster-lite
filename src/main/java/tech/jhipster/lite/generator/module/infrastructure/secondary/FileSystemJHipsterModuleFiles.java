package tech.jhipster.lite.generator.module.infrastructure.secondary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.module.domain.TemplatedFile;
import tech.jhipster.lite.generator.module.domain.TemplatedFiles;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterProjectFolder;

@Repository
class FileSystemJHipsterModuleFiles {

  private static final Logger log = LoggerFactory.getLogger(FileSystemJHipsterModuleFiles.class);

  void create(JHipsterProjectFolder projectFolder, TemplatedFiles files) {
    files.get().forEach(writeFile(projectFolder));
  }

  private Consumer<TemplatedFile> writeFile(JHipsterProjectFolder projectFolder) {
    return file -> {
      Path filePath = file.path(projectFolder);

      try {
        Files.createDirectories(file.folder(projectFolder));
        Files.writeString(filePath, file.content());

        log.debug("{} added", filePath);
      } catch (IOException e) {
        throw new GeneratorException("Can't write file to " + filePath.toString() + ": " + e.getMessage(), e);
      }
    };
  }
}
