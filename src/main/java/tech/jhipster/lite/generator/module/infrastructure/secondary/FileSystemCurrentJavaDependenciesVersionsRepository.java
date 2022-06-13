package tech.jhipster.lite.generator.module.infrastructure.secondary;

import java.util.List;
import java.util.regex.Pattern;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.generator.module.domain.FilesReader;
import tech.jhipster.lite.generator.module.domain.javadependency.CurrentJavaDependenciesVersions;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependenciesCurrentVersionsRepository;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependencyVersion;

@Repository
class FileSystemCurrentJavaDependenciesVersionsRepository implements JavaDependenciesCurrentVersionsRepository {

  private static final String CURRENT_VERSIONS_FILE = "/generator/dependencies/pom.xml";
  private static final Pattern VERSIONS_PATTERN = Pattern.compile("<([^>]+)\\.version>([^>]+)<\\/");

  private final CurrentJavaDependenciesVersions versions;

  public FileSystemCurrentJavaDependenciesVersionsRepository() {
    versions = readVersions();
  }

  @Generated
  private CurrentJavaDependenciesVersions readVersions() {
    List<JavaDependencyVersion> readVersions = extractVersions(FilesReader.readContent(CURRENT_VERSIONS_FILE));

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
