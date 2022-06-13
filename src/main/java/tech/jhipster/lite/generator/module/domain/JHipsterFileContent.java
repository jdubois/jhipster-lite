package tech.jhipster.lite.generator.module.domain;

import java.nio.file.Path;
import tech.jhipster.lite.error.domain.Assert;

class JHipsterFileContent {

  private final Path source;

  JHipsterFileContent(Path source) {
    Assert.notNull("source", source);

    this.source = source;
  }

  public String read(JHipsterModuleContext context) {
    Assert.notNull("context", context);

    String rawContent = FilesReader.readContent(source.toString());
    return ArgumentsReplacer.replaceArguments(rawContent, context.get());
  }
}
