package tech.jhipster.lite.module.infrastructure.secondary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.module.domain.replacement.ContentReplacement;
import tech.jhipster.lite.module.domain.replacement.JHipsterModuleReplacements;

class FileSystemReplacer {

  public void handle(JHipsterProjectFolder projectFolder, JHipsterModuleReplacements replacements) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("replacements", replacements);

    replacements.replacements().forEach(applyReplacement(projectFolder));
  }

  private Consumer<ContentReplacement> applyReplacement(JHipsterProjectFolder projectFolder) {
    return replacement -> {
      Path filePath = projectFolder.filePath(replacement.file());

      try {
        String content = Files.readString(filePath);
        String updatedContent = replacement.apply(content);

        Files.writeString(filePath, updatedContent, StandardOpenOption.TRUNCATE_EXISTING);
      } catch (IOException e) {
        replacement.handleError(e);
      }
    };
  }
}
