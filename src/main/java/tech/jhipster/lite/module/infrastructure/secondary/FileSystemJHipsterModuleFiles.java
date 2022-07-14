package tech.jhipster.lite.module.infrastructure.secondary;

import static java.nio.file.attribute.PosixFilePermission.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.module.domain.TemplatedFile;
import tech.jhipster.lite.module.domain.TemplatedFiles;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.projectfile.domain.ProjectFilesReader;

@Repository
class FileSystemJHipsterModuleFiles {

  private static final Logger log = LoggerFactory.getLogger(FileSystemJHipsterModuleFiles.class);
  private static final Set<PosixFilePermission> EXECUTABLE_FILE_PERMISSIONS = buildExecutableFilePermission();

  private final ProjectFilesReader files;

  public FileSystemJHipsterModuleFiles(ProjectFilesReader files) {
    this.files = files;
  }

  private static Set<PosixFilePermission> buildExecutableFilePermission() {
    return Set.of(OWNER_READ, OWNER_WRITE, OWNER_EXECUTE, GROUP_READ, GROUP_WRITE, GROUP_EXECUTE);
  }

  void create(JHipsterProjectFolder projectFolder, TemplatedFiles files) {
    files.get().forEach(writeFile(projectFolder));
  }

  private Consumer<TemplatedFile> writeFile(JHipsterProjectFolder projectFolder) {
    return file -> {
      Path filePath = file.path(projectFolder);

      try {
        Files.createDirectories(file.folder(projectFolder));
        Files.write(filePath, file.content(files));

        setExecutable(file, filePath);

        log.debug("{} added", filePath);
      } catch (IOException e) {
        throw new GeneratorException("Can't write file to " + filePath.toString() + ": " + e.getMessage(), e);
      }
    };
  }

  @Generated(reason = "Ensuring posix FS whill be a nightmare :)")
  private void setExecutable(TemplatedFile file, Path filePath) throws IOException {
    if (!FileUtils.isPosix()) {
      return;
    }

    if (file.isNotExecutable()) {
      return;
    }

    Files.setPosixFilePermissions(filePath, EXECUTABLE_FILE_PERMISSIONS);
  }
}
