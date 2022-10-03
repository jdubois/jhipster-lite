package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import java.util.List;
import java.util.regex.Pattern;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.module.domain.ProjectFilesReader;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesVersions;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyVersion;

@Repository
@Order(Ordered.LOWEST_PRECEDENCE)
class FileSystemJavaDependenciesReader implements JavaDependenciesReader {

  private static final String CURRENT_VERSIONS_FILE = "/generator/dependencies/pom.xml";
  private static final Pattern VERSIONS_PATTERN = Pattern.compile("<([^>]+)\\.version>([^>]+)<\\/");

  private final ProjectFilesReader files;

  public FileSystemJavaDependenciesReader(ProjectFilesReader files) {
    this.files = files;
  }

  @Override
  public JavaDependenciesVersions get() {
    List<JavaDependencyVersion> versions = readVersions(files.readString(CURRENT_VERSIONS_FILE));

    return new JavaDependenciesVersions(versions);
  }

  private List<JavaDependencyVersion> readVersions(String content) {
    return VERSIONS_PATTERN.matcher(content).results().map(result -> new JavaDependencyVersion(result.group(1), result.group(2))).toList();
  }
}
