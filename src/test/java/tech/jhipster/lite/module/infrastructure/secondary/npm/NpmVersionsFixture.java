package tech.jhipster.lite.module.infrastructure.secondary.npm;

import java.util.List;
import tech.jhipster.lite.module.domain.ProjectFilesReader;
import tech.jhipster.lite.module.domain.npm.NpmVersions;

public final class NpmVersionsFixture {

  private NpmVersionsFixture() {}

  public static NpmVersions npmVersions(ProjectFilesReader filesReader) {
    return new JHipsterNpmVersions(List.of(new FileSystemNpmVersionReader(filesReader)));
  }
}
