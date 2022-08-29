package tech.jhipster.lite.module.domain.file;

import java.util.ArrayList;
import java.util.Collection;
import tech.jhipster.lite.common.domain.JHipsterCollections;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;

public class JHipsterModuleFiles {

  private final Collection<JHipsterModuleFile> filesToAdd;
  private final JHipsterFilesToDelete filesToDelete;
  private final JHipsterFilesToMove filesToMove;

  private JHipsterModuleFiles(JHipsterModuleFilesBuilder builder) {
    filesToAdd = JHipsterCollections.immutable(builder.filesToAdd);
    filesToMove = new JHipsterFilesToMove(builder.filesToMove);
    filesToDelete = new JHipsterFilesToDelete(builder.filesToDelete);
  }

  public static JHipsterModuleFilesBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleFilesBuilder(module);
  }

  public Collection<JHipsterModuleFile> filesToAdd() {
    return filesToAdd;
  }

  public JHipsterFilesToMove filesToMove() {
    return filesToMove;
  }

  public JHipsterFilesToDelete filesToDelete() {
    return filesToDelete;
  }

  public static class JHipsterModuleFilesBuilder {

    private final JHipsterModuleBuilder module;
    private final Collection<JHipsterModuleFile> filesToAdd = new ArrayList<>();
    private final Collection<JHipsterFileToMove> filesToMove = new ArrayList<>();
    private final Collection<JHipsterProjectFilePath> filesToDelete = new ArrayList<>();

    private JHipsterModuleFilesBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleFilesBuilder add(JHipsterSource source, JHipsterDestination destination) {
      filesToAdd.add(new JHipsterModuleFile(new JHipsterFileContent(source), destination, false));

      return this;
    }

    public JHipsterModuleFilesBuilder addExecutable(JHipsterSource source, JHipsterDestination destination) {
      filesToAdd.add(new JHipsterModuleFile(new JHipsterFileContent(source), destination, true));

      return this;
    }

    public JHipsterModuleFileBatchBuilder batch(JHipsterSource source, JHipsterDestination destination) {
      return new JHipsterModuleFileBatchBuilder(source, destination, this);
    }

    public JHipsterModuleFilesBuilder move(JHipsterProjectFilePath source, JHipsterDestination destination) {
      filesToMove.add(new JHipsterFileToMove(source, destination));

      return this;
    }

    public JHipsterModuleFilesBuilder delete(JHipsterProjectFilePath path) {
      Assert.notNull("path", path);

      filesToDelete.add(path);

      return this;
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

    public JHipsterModuleFileBatchBuilder addTemplate(String file) {
      return add(source.template(file), destination.append(file));
    }

    public JHipsterModuleFileBatchBuilder addFile(String file) {
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
