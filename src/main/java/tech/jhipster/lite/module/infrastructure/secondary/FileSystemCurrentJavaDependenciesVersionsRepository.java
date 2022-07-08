package tech.jhipster.lite.module.infrastructure.secondary;

import java.util.List;
import java.util.regex.Pattern;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.common.domain.ProjectFilesReader;
import tech.jhipster.lite.module.domain.javadependency.CurrentJavaDependenciesVersions;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesCurrentVersionsRepository;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyVersion;

@Repository
class FileSystemCurrentJavaDependenciesVersionsRepository implements JavaDependenciesCurrentVersionsRepository {

  private static final String CURRENT_VERSIONS_FILE = "/generator/dependencies/pom.xml";
  private static final Pattern VERSIONS_PATTERN = Pattern.compile("<([^>]+)\\.version>([^>]+)<\\/");

  private final ProjectFilesReader files;
  private final CurrentJavaDependenciesVersions versions;

  public FileSystemCurrentJavaDependenciesVersionsRepository(ProjectFilesReader files) {
    this.files = files;

    versions = readVersions();
  }

  private CurrentJavaDependenciesVersions readVersions() {
    List<JavaDependencyVersion> readVersions = extractVersions(files.readString(CURRENT_VERSIONS_FILE));

    return new CurrentJavaDependenciesVersions(readVersions);
  }

  private List<JavaDependencyVersion> extractVersions(String content) {
    return VERSIONS_PATTERN.matcher(content).results().map(result -> new JavaDependencyVersion(result.group(1), result.group(2))).toList();
  }

  @Override
  public CurrentJavaDependenciesVersions get() {
    return versions;
  }
}
