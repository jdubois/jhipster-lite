package tech.jhipster.lite.generator.project.infrastructure.secondary;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.function.Consumer;
import org.springframework.stereotype.Repository;
import org.zeroturnaround.zip.ZipException;
import org.zeroturnaround.zip.ZipUtil;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.project.domain.FilePath;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@Repository
public class ProjectLocalRepository implements ProjectRepository {

  private static final String FILE_SEPARATOR = "/";

  @Override
  public void add(Collection<ProjectFile> files) {
    Assert.field("files", files).notEmpty().noNullElement();

    files.forEach(file -> {
      try (InputStream in = getInputStream(file.source())) {
        String destinationFolder = FileUtils.getPath(file.project().getFolder(), file.destination().folder());
        Path destinationPath = FileUtils.getPathOf(destinationFolder, file.destination().file());

        FileUtils.createFolder(destinationFolder);
        Files.copy(in, destinationPath, StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException ex) {
        throw new GeneratorException("The file '" + file.destination().file() + "' can't be added");
      }
    });
  }

  private static InputStream getInputStream(FilePath path) {
    String resolvedPath = getPath(TEMPLATE_FOLDER, path.folder(), path.file());

    InputStream in = ProjectLocalRepository.class.getResourceAsStream(FILE_SEPARATOR + resolvedPath);

    if (in == null) {
      throw new GeneratorException("File '" + resolvedPath + "' not found in classpath");
    }

    return in;
  }

  @Override
  public void template(Collection<ProjectFile> files) {
    Assert.field("files", files).notEmpty().noNullElement();

    files.forEach(templatizeFile());
  }

  private Consumer<ProjectFile> templatizeFile() {
    return file -> {
      try {
        String result = templatizeContent(file);

        writeTemplatizedContent(file, result);
      } catch (IOException ex) {
        throw new GeneratorException("The file '" + file.destination().file() + "' can't be added");
      }
    };
  }

  private String templatizeContent(ProjectFile file) throws IOException {
    String sourcePath = FileUtils.getPath(TEMPLATE_FOLDER, file.source().folder(), MustacheUtils.withExt(file.source().file()));

    return MustacheUtils.template(sourcePath, file.project().getConfig());
  }

  private void writeTemplatizedContent(ProjectFile file, String result) {
    write(file.project(), result, file.destination().folder(), MustacheUtils.withoutExt(file.destination().file()));
  }

  @Override
  public void rename(Project project, String source, String sourceFilename, String destinationFilename) {
    try {
      FileUtils.rename(getPath(project.getFolder(), source), sourceFilename, destinationFilename);
    } catch (IOException e) {
      throw new GeneratorException("Error when renaming file: " + sourceFilename + " -> " + destinationFilename, e);
    }
  }

  @Override
  public boolean containsRegexp(Project project, String source, String sourceFilename, String regexp) {
    try {
      String text = read(getPath(project.getFolder(), source, sourceFilename));
      return FileUtils.containsRegexp(text, regexp);
    } catch (IOException e) {
      throw new GeneratorException("Error when reading text from '" + sourceFilename + "'");
    }
  }

  @Override
  public void replaceText(Project project, String source, String sourceFilename, String oldText, String newText) {
    try {
      String currentText = read(getPath(project.getFolder(), source, sourceFilename));
      String updatedText = FileUtils.replace(currentText, oldText, newText);
      write(project, updatedText, source, sourceFilename);
    } catch (IOException e) {
      throw new GeneratorException(getErrorWritingMessage(sourceFilename));
    }
  }

  @Override
  public void write(Project project, String text, String destination, String destinationFilename) {
    Assert.notNull("text", text);

    String projectDestination = getPath(project.getFolder(), destination);
    String projectDestinationFilename = getPath(projectDestination, destinationFilename);

    try {
      FileUtils.createFolder(projectDestination);
      FileUtils.write(projectDestinationFilename, text, project.getEndOfLine());
    } catch (IOException e) {
      throw new GeneratorException(getErrorWritingMessage(projectDestinationFilename));
    }
  }

  @Override
  public String zip(Project project) {
    File workingDir = new File(project.getFolder());
    String filename = workingDir.getName() + ".zip";
    try {
      ZipUtil.pack(workingDir, new File(tmpDir() + FileSystems.getDefault().getSeparator() + filename));
      return filename;
    } catch (ZipException e) {
      throw new GeneratorException("Error when zipping " + project.getFolder(), e);
    }
  }

  private String getErrorWritingMessage(String filename) {
    return "Error when writing text to '" + filename + "'";
  }
}
