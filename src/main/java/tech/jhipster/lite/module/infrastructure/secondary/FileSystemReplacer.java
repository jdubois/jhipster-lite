package tech.jhipster.lite.module.infrastructure.secondary;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.module.domain.replacement.ContentReplacer;
import tech.jhipster.lite.module.domain.replacement.JHipsterModuleReplacements;

class FileSystemReplacer {

  public void handle(JHipsterProjectFolder projectFolder, JHipsterModuleReplacements replacements) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("replacements", replacements);

    replacements.replacements().forEach(applyReplacement(projectFolder));
  }

  private Consumer<ContentReplacer> applyReplacement(JHipsterProjectFolder projectFolder) {
    return replacement -> {
      Path filePath = projectFolder.filePath(replacement.file().get());

      try {
        String content = Files.readString(filePath);
        String updatedContent = replacement.apply(content);

        Files.writeString(filePath, updatedContent.replace("\r\n", LINE_BREAK), StandardCharsets.UTF_8);
      } catch (IOException e) {
        replacement.handleError(e);
      }
    };
  }
}
