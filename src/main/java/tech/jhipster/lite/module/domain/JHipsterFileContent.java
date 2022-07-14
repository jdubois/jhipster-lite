package tech.jhipster.lite.module.domain;

import java.nio.charset.StandardCharsets;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.projectfile.domain.ProjectFilesReader;

class JHipsterFileContent {

  private final JHipsterSource source;

  JHipsterFileContent(JHipsterSource source) {
    Assert.notNull("source", source);

    this.source = source;
  }

  public byte[] read(ProjectFilesReader files, JHipsterModuleContext context) {
    Assert.notNull("files", files);
    Assert.notNull("context", context);

    if (source.isNotTemplate()) {
      return files.readBytes(source.get().toString());
    }

    String rawContent = files.readString(source.get().toString());
    return ArgumentsReplacer.replaceArguments(rawContent, context.get()).getBytes(StandardCharsets.UTF_8);
  }
}
