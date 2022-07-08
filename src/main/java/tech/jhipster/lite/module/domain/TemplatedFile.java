package tech.jhipster.lite.module.domain;

import java.nio.file.Path;
import tech.jhipster.lite.common.domain.ProjectFilesReader;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

public class TemplatedFile {

  private final JHipsterModuleFile file;
  private final JHipsterModuleContext context;

  private TemplatedFile(TemplatedFileBuilder builder) {
    Assert.notNull("file", builder.file);
    Assert.notNull("context", builder.context);

    file = builder.file;
    context = builder.context;
  }

  static TemplatedFileBuilder builder() {
    return new TemplatedFileBuilder();
  }

  public Path folder(JHipsterProjectFolder projectFolder) {
    Assert.notNull("projectFolder", projectFolder);

    return file.destination().folder(projectFolder);
  }

  public Path path(JHipsterProjectFolder projectFolder) {
    Assert.notNull("projectFolder", projectFolder);

    return file.destination().pathInProject(projectFolder);
  }

  public byte[] content(ProjectFilesReader files) {
    return file.content().read(files, context);
  }

  public boolean isNotExecutable() {
    return !file.executable();
  }

  static class TemplatedFileBuilder {

    private JHipsterModuleFile file;
    private JHipsterModuleContext context;

    public TemplatedFileBuilder file(JHipsterModuleFile file) {
      this.file = file;

      return this;
    }

    public TemplatedFileBuilder context(JHipsterModuleContext context) {
      this.context = context;

      return this;
    }

    public TemplatedFile build() {
      return new TemplatedFile(this);
    }
  }
}
