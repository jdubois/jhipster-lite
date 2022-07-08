package tech.jhipster.lite.module.domain;

import java.util.ArrayList;
import java.util.Collection;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;

public class JHipsterModuleFiles {

  private final Collection<JHipsterModuleFile> files;

  private JHipsterModuleFiles(JHipsterModuleFilesBuilder builder) {
    files = builder.files;
  }

  static JHipsterModuleFilesBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleFilesBuilder(module);
  }

  public Collection<JHipsterModuleFile> get() {
    return files;
  }

  public static class JHipsterModuleFilesBuilder {

    private final JHipsterModuleBuilder module;
    private final Collection<JHipsterModuleFile> files = new ArrayList<>();

    private JHipsterModuleFilesBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleFilesBuilder add(JHipsterSource source, JHipsterDestination destination) {
      files.add(new JHipsterModuleFile(new JHipsterFileContent(source), destination, false));

      return this;
    }

    public JHipsterModuleFilesBuilder addExecutable(JHipsterSource source, JHipsterDestination destination) {
      files.add(new JHipsterModuleFile(new JHipsterFileContent(source), destination, true));

      return this;
    }

    public JHipsterModuleFileBatchBuilder batch(JHipsterSource source, JHipsterDestination destination) {
      return new JHipsterModuleFileBatchBuilder(source, destination, this);
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    public JHipsterModuleFiles build() {
      return new JHipsterModuleFiles(this);
    }
  }

  public static class JHipsterModuleFileBatchBuilder {

    private final JHipsterSource source;
    private final JHipsterDestination destination;
    private final JHipsterModuleFilesBuilder files;

    private JHipsterModuleFileBatchBuilder(JHipsterSource source, JHipsterDestination destination, JHipsterModuleFilesBuilder files) {
      this.source = source;
      this.destination = destination;
      this.files = files;
    }

    public JHipsterModuleFileBatchBuilder template(String file) {
      return add(source.template(file), destination.append(file));
    }

    public JHipsterModuleFileBatchBuilder file(String file) {
      return add(source.file(file), destination.append(file));
    }

    private JHipsterModuleFileBatchBuilder add(JHipsterSource source, JHipsterDestination destination) {
      files.add(source, destination);

      return this;
    }

    public JHipsterModuleFilesBuilder and() {
      return files;
    }
  }
}
