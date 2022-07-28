package tech.jhipster.lite.project.infrastructure.secondary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.zeroturnaround.zip.ZipException;
import org.zeroturnaround.zip.ZipUtil;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.project.domain.ProjectPath;
import tech.jhipster.lite.project.domain.download.Project;
import tech.jhipster.lite.project.domain.download.ProjectName;

class FileSystemProjectDownloader {

  private static final Pattern PROJECT_NAME_PATTERN = Pattern.compile("\"description\": *\"([^\"]+)\"");

  public Optional<Project> download(ProjectPath path) {
    Assert.notNull("path", path);

    Path source = Paths.get(path.get());

    if (notAProject(source)) {
      return Optional.empty();
    }

    try {
      ProjectName projectName = readProjectName(source);

      byte[] content = zip(source);

      return Optional.of(new Project(projectName, content));
    } catch (IOException | ZipException e) {
      throw new ProjectZippingException(e);
    }
  }

  private boolean notAProject(Path source) {
    return Files.notExists(source) || !Files.isDirectory(source);
  }

  private ProjectName readProjectName(Path source) throws IOException {
    Path packageJsonPath = source.resolve("package.json");

    if (Files.notExists(packageJsonPath)) {
      return ProjectName.DEFAULT;
    }

    Matcher matcher = PROJECT_NAME_PATTERN.matcher(Files.readString(packageJsonPath));

    if (!matcher.find()) {
      return ProjectName.DEFAULT;
    }

    return new ProjectName(matcher.group(1));
  }

  private byte[] zip(Path source) throws IOException {
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      ZipUtil.pack(source.toFile(), out);

      return out.toByteArray();
    }
  }
}
