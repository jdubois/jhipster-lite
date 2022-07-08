package tech.jhipster.lite.npm.infrastructure.secondary;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.common.domain.Enums;
import tech.jhipster.lite.common.domain.ProjectFilesReader;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.npm.domain.NpmPackageName;
import tech.jhipster.lite.npm.domain.NpmVersion;
import tech.jhipster.lite.npm.domain.NpmVersionSource;
import tech.jhipster.lite.npm.domain.NpmVersions;
import tech.jhipster.lite.npm.domain.UnknownNpmPackageException;

@Repository
public class FileSystemNpmVersions implements NpmVersions {

  private final ProjectFilesReader projectFiles;

  public FileSystemNpmVersions(ProjectFilesReader projectFiles) {
    this.projectFiles = projectFiles;
  }

  @Override
  public NpmVersion get(NpmPackageName packageName, NpmVersionSource source) {
    Assert.notNull("packageName", packageName);
    Assert.notNull("source", source);

    String sourceFolder = sourceFolder(source);

    Matcher matcher = dependencyPattern(packageName).matcher(readVersionsFile(sourceFolder));
    if (!matcher.find()) {
      throw new UnknownNpmPackageException(packageName, sourceFolder);
    }

    return new NpmVersion(matcher.group(2));
  }

  private String sourceFolder(NpmVersionSource source) {
    return Enums.map(source, NpmVersionSourceFolder.class).folder();
  }

  private String readVersionsFile(String sourceFolder) {
    return projectFiles.readString("/generator/dependencies/" + sourceFolder + "/package.json");
  }

  private Pattern dependencyPattern(NpmPackageName packageName) {
    String pattern = new StringBuilder()
      .append("\"(devDependencies|dependencies)\"\\s*:\\s*\\{.*\"")
      .append(packageName.get())
      .append("\"\\s*:\\s*\"([^\"]+)\"")
      .toString();

    return Pattern.compile(pattern, Pattern.DOTALL);
  }
}
