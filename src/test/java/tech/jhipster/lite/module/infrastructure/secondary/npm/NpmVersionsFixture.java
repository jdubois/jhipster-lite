package tech.jhipster.lite.module.infrastructure.secondary.npm;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.ProjectFiles;
import tech.jhipster.lite.module.domain.npm.NpmVersions;

public final class NpmVersionsFixture {

  private NpmVersionsFixture() {}

  public static NpmVersions npmVersions(ProjectFiles filesReader) {
    return new JHipsterNpmVersions(List.of(new FileSystemNpmVersionReader(filesReader)));
  }

  public static NpmVersions npmVersions(ProjectFiles filesReader, Collection<NpmVersionsReader> customReaders) {
    Assert.notNull("customReaders", customReaders);

    return new JHipsterNpmVersions(Stream.concat(Stream.of(new FileSystemNpmVersionReader(filesReader)), customReaders.stream()).toList());
  }
}
